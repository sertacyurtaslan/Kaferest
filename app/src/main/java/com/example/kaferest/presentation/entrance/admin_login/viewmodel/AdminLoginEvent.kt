package com.example.kaferest.presentation.entrance.admin_login.viewmodel

sealed class AdminLoginEvent {
    data class LoginOwner(
        val ownerMail: String = "",
        val ownerPassword: String = ""
    ): AdminLoginEvent()
}