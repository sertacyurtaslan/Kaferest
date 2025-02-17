package com.example.kaferest.presentation.auth.menu.shop_login.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class ShopLoginState(
    val ownerMail: String = "",
    val ownerPassword: String = "",
    val matchError: String? = null,
    val isLoading: Boolean = false,
    val isNewShop: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)