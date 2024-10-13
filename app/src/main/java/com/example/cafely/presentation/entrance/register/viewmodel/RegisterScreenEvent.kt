package com.example.financecompose.presentation.entrance.register.viewmodel

sealed class RegisterScreenEvent {
    data class RegisterUser(
        val userName: String = "",
        val userMail: String = "",
        val userPassword: String = ""
    ): RegisterScreenEvent()
}