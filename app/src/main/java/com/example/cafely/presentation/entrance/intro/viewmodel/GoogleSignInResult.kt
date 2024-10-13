package com.example.cafely.presentation.entrance.intro.viewmodel

import com.example.cafely.domain.model.User

data class GoogleSignInResult(
    val data: User?,
    val errorMessage: String?,
    val isNewUser: Boolean = false,
    val isSuccess: Boolean = false
)

