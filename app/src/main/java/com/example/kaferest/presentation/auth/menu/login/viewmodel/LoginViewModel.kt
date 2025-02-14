package com.example.kaferest.presentation.entrance.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.User
import com.example.kaferest.presentation.entrance.admin_login.viewmodel.AdminLoginEvent
import com.example.kaferest.util.CurrentDate
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState

    private fun loginUser(email: String, password: String) =
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    user?.let {
                        val userData = User(
                            userId = it.uid,
                            userName = it.displayName ?: "",
                            userEmail = it.email ?: "",
                            userCreationDate = CurrentDate().getFormattedDate()
                        )

                        viewModelScope.launch {
                            try {
                                userManager.saveUser(userData)
                                _uiState.value = _uiState.value.copy(
                                    successMessage = "Login successful",
                                    isLoading = false
                                )
                            } catch (e: Exception) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    errorMessage = "Error saving user data: ${e.message}"
                                )
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.localizedMessage,
                        matchError = "Check your email and password"
                    )
                }
        }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.LoginUser -> {
                loginUser(
                    event.userMail,
                    event.userPassword
                )
            }
            else -> {/*Else*/}
        }
    }
}