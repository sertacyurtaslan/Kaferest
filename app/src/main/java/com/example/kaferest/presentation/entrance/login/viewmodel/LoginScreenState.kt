package com.example.kaferest.presentation.entrance.login.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val matchError: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
