package com.example.kaferest.presentation.entrance.forgot_password.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.presentation.menu.home.viewmodel.HomeScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _uiState = mutableStateOf(ForgotPasswordScreenState())
    val uiState: State<ForgotPasswordScreenState> = _uiState

    private val _canResend = MutableStateFlow(true)
    val canResend: StateFlow<Boolean> = _canResend.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

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

    fun resendVerificationEmail() = viewModelScope.launch {
        if (_canResend.value) {
            _canResend.value = false
            _remainingSeconds.value = 60

            viewModelScope.launch {
                while (_remainingSeconds.value > 0) {
                    delay(1000)
                    _remainingSeconds.value--
                }
                _canResend.value = true
            }
            println("Mail to resend:"+_uiState.value.email)

            sendPasswordResetEmail(_uiState.value.email)
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
        }
    }
}