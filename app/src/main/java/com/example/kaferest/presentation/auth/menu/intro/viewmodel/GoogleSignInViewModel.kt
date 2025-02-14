package com.example.kaferest.presentation.auth.menu.intro.viewmodel

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.repository.KaferestRepository
import com.example.kaferest.domain.manager.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val kaferestRepository: KaferestRepository,
    private val userManager: UserManager,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignInState())
    val state: StateFlow<GoogleSignInState> = _state.asStateFlow()

    init {
        checkSignInStatus()
    }

    private fun checkSignInStatus() = viewModelScope.launch {
        if (googleAuthUiClient.isUserSignedIn()) {
            userManager.user.collect { user ->
                user?.let {
                    _state.value = GoogleSignInState(
                        isSignInSuccessful = true,
                        user = it
                    )
                }
            }
        }
    }

    private fun onSignInResult(signInResult: GoogleSignInResult) =
        viewModelScope.launch {
            if (signInResult.isSuccess) {
                println("GoogleSignIn: Sign in successful")
                val userId = signInResult.data?.userId
                if (userId != null) {
                    println("GoogleSignIn: User ID: $userId")
                    try {
                        withContext(Dispatchers.IO) {
                            // First check if user exists in Firestore
                            val existingUser = kaferestRepository.getUser(userId)
                            
                            if (existingUser != null) {
                                println("GoogleSignIn: Existing user found, signing in")
                                _state.value = GoogleSignInState(
                                    isSignInSuccessful = true, 
                                    user = existingUser
                                )
                            } else {
                                // Check if email exists in authentication but not in Firestore
                                val userByEmail = signInResult.data.userEmail?.let {
                                    kaferestRepository.getUserByEmail(
                                        it
                                    )
                                }
                                
                                if (userByEmail != null) {
                                    println("GoogleSignIn: User with email exists, updating auth ID")
                                    // Update the existing user with new auth ID
                                    val updatedUser = userByEmail.copy(userId = userId)
                                    kaferestRepository.updateUser(updatedUser)
                                    _state.value = GoogleSignInState(
                                        isSignInSuccessful = true,
                                        user = updatedUser
                                    )
                                } else {
                                    println("GoogleSignIn: Creating new user")
                                    val newUser = kaferestRepository.createUser(signInResult.data)
                                    _state.value = GoogleSignInState(
                                        isSignInSuccessful = true,
                                        user = newUser,
                                        isNewUser = true
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                        when {
                            e.message?.contains("offline", ignoreCase = true) == true -> {
                                println("GoogleSignIn: Offline error - ${e.message}")
                                _state.value = GoogleSignInState(
                                    signInError = "Unable to connect. Please check your internet connection.",
                                    user = signInResult.data
                                )
                            }
                            e is CancellationException -> throw e
                            else -> {
                                println("GoogleSignIn: Error accessing database - ${e.message}")
                                _state.value = GoogleSignInState(
                                    signInError = "An error occurred while signing in. Please try again.",
                                    user = signInResult.data
                                )
                            }
                        }
                    }
                } else {
                    println("GoogleSignIn: User ID is null")
                    _state.value = GoogleSignInState(signInError = "User ID is null")
                }
            } else {
                println("GoogleSignIn: Sign in failed - ${signInResult.errorMessage}")
                _state.value = GoogleSignInState(signInError = signInResult.errorMessage)
            }
        }

    suspend fun signOut() = viewModelScope.launch {
        try {
            googleAuthUiClient.signOut()
            _state.value = GoogleSignInState() // Reset state
        } catch (e: Exception) {
            println("Error signing out: ${e.message}")
        }
    }

    suspend fun signInWithIntent(intent: Intent) {
        try {
            val result = googleAuthUiClient.signInWithIntent(intent)
            onSignInResult(result)
        } catch (e: Exception) {
            _state.value = GoogleSignInState(signInError = e.message)
        }
    }

    suspend fun signIn(): IntentSender? {
        return googleAuthUiClient.signIn()
    }
}
