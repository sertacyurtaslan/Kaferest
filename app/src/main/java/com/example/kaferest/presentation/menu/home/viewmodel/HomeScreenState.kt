package com.example.kaferest.presentation.menu.home.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class HomeScreenState(
    val email: String = "",
    val password: String = "",
    val matchError: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
