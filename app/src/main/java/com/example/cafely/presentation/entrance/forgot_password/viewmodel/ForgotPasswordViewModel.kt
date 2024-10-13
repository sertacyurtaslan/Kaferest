package com.example.cafely.presentation.entrance.forgot_password.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState

    private fun sendPasswordResetEmail(email: String) {
        if (email.isNotEmpty()) {
            viewModelScope.launch {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _uiState.value = _uiState.value.copy(successMessage = "Mail sent successfully.")
                        } else {
                            _uiState.value = _uiState.value.copy(errorMessage = "No matched mail.")
                        }
                    }
            }
        }
        else{
            _uiState.value = _uiState.value.copy(errorMessage = "Empty input.")
        }
    }

    fun onEvent(event: ForgotPasswordScreenEvent) {
        when (event) {
            is ForgotPasswordScreenEvent.SendPasswordResetMail -> {
                viewModelScope.launch {
                    sendPasswordResetEmail(
                        event.userMail
                    )
                }
            }

            else -> {}
        }
    }
}