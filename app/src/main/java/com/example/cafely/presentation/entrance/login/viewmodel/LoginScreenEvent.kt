package com.example.financecompose.presentation.entrance.login.viewmodel

sealed class LoginScreenEvent {
    data class LoginUser(
        val userMail: String = "",
        val userPassword: String = ""
    ): LoginScreenEvent()
}