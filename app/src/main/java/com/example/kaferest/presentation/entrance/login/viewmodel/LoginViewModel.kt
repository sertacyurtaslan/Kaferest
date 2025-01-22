package com.example.kaferest.presentation.entrance.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.presentation.menu.home.viewmodel.HomeScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState

    private fun loginUser(email: String, password: String) =
        viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        _uiState.value = _uiState.value.copy(successMessage = "Login successful")
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    .addOnFailureListener { exception ->
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _uiState.value = _uiState.value.copy(
                            errorMessage = exception.localizedMessage,
                            matchError = "Check your email and password"
                            )
                    }
            }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.LoginUser -> {
                viewModelScope.launch {
                    loginUser(
                        event.userMail,
                        event.userPassword
                    )
                }
            }

            else -> {/*Else*/}
        }
    }
}