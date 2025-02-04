package com.example.kaferest.presentation.entrance.admin_login.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class AdminLoginState(
    val ownerMail: String = "",
    val ownerPassword: String = "",
    val matchError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)