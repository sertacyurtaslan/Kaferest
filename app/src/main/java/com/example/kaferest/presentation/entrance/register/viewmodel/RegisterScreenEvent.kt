package com.example.kaferest.presentation.entrance.register.viewmodel

sealed class RegisterScreenEvent {
    data class RegisterUser(
        val userName: String = "",
        val userMail: String = "",
        val userPassword: String = ""
    ): RegisterScreenEvent()

    data class SendVerificationMail(
        val userName: String = "",
        val userMail: String = "",
        val userPassword: String = ""
    ): RegisterScreenEvent()

    data class VerifyCodeAndRegister(
        val inputCode: String = ""
    ): RegisterScreenEvent()

    data class CheckEmailExists(
        val email: String
    ): RegisterScreenEvent()
}