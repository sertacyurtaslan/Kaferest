package com.example.kaferest.presentation.shop.menu.home.viewmodel

sealed class ShopCreationEvent {
    data class UpdateShopName(
        val shopName: String = "",
    ): ShopCreationEvent()

    data class UpdateShopAdress(
        val shopAddress: String = "",
    ): ShopCreationEvent()
}
