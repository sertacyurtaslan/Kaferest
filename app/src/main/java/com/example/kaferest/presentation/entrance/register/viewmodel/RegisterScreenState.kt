package com.example.kaferest.presentation.entrance.register.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterScreenState(
    val userName: String = "",
    val userMail: String = "",
    val userPassword: String = "",
    val verificationCode: String = "",
    val emailError: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isRegistered: Boolean = false,
    val isMailVerified: Boolean = false,
    val isLoading: Boolean = false,
)
