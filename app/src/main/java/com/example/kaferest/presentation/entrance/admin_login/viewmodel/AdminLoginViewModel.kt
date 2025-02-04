package com.example.kaferest.presentation.entrance.admin_login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.User
import com.example.kaferest.util.CurrentDate
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiState = mutableStateOf(AdminLoginState())
    val uiState: State<AdminLoginState> = _uiState

    private fun loginWithEmailPassword(email: String, password: String) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            
            if (firebaseUser != null) {
                // Create admin user data
                val adminData = User(
                    userId = firebaseUser.uid,
                    userName = firebaseUser.displayName ?: "",
                    userEmail = email,
                    userCreationDate = CurrentDate().getFormattedDate()
                )
                
                // Save admin data using UserManager
                userManager.saveAdmin(adminData)
                
                _uiState.value = _uiState.value.copy(
                    successMessage = "Login successful",
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    matchError = "Invalid credentials",
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = when {
                    e.message?.contains("password is invalid", ignoreCase = true) == true -> 
                        "Invalid password"
                    e.message?.contains("no user record", ignoreCase = true) == true -> 
                        "No account found with this email"
                    else -> e.message ?: "Authentication failed"
                },
                matchError = "Check your email and password"
            )
        }
    }

    fun onEvent(event: AdminLoginEvent) {
        when (event) {
            is AdminLoginEvent.LoginOwner -> {
                loginWithEmailPassword(event.ownerMail, event.ownerPassword)
            }

        }
    }
}