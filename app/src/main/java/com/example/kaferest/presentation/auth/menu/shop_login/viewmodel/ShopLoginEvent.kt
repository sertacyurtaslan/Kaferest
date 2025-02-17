package com.example.kaferest.presentation.auth.menu.shop_login.viewmodel

sealed class ShopLoginEvent {
    data class LoginOwner(
        val ownerMail: String = "",
        val ownerPassword: String = ""
    ): ShopLoginEvent()
}

