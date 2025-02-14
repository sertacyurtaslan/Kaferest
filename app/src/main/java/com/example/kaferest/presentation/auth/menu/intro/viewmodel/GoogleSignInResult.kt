package com.example.kaferest.presentation.auth.menu.intro.viewmodel

import com.example.kaferest.domain.model.User

data class GoogleSignInResult(
    val data: User?,
    val errorMessage: String?,
    val isNewUser: Boolean = false,
    val isSuccess: Boolean = false
)

