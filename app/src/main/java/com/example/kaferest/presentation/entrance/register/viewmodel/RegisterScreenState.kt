package com.example.kaferest.presentation.entrance.register.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterScreenState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val verificationCode: String = "",
    val emailError: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isRegistered: Boolean = false
)
