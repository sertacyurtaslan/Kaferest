package com.example.cafely.presentation.entrance.forgot_password.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class ForgotPasswordScreenState(
    val email: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)
