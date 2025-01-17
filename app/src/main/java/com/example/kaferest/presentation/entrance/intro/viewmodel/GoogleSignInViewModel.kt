package com.example.kaferest.presentation.entrance.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.repository.CafelyRepository
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
    private val cafelyRepository: CafelyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignInState())
    val state: StateFlow<GoogleSignInState> = _state.asStateFlow()

    fun onSignInResult(signInResult: GoogleSignInResult) =
        viewModelScope.launch {
            if (signInResult.isSuccess) {
                println("GoogleSignIn: Sign in successful")
                val userId = signInResult.data?.userId
                if (userId != null) {
                    println("GoogleSignIn: User ID: $userId")
                    try {
                        withContext(Dispatchers.IO) {
                            val user = cafelyRepository.getUser(userId)
                            if (user != null) {
                                println("GoogleSignIn: Existing user found")
                                _state.value = GoogleSignInState(isSignInSuccessful = true, user = user)
                            } else {
                                println("GoogleSignIn: Creating new user")
                                val newUser = cafelyRepository.createUser(signInResult.data)
                                _state.value = GoogleSignInState(
                                    isSignInSuccessful = true,
                                    user = newUser,
                                    isNewUser = true
                                )
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
}
