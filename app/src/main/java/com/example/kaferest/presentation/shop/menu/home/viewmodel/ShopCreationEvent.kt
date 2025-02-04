package com.example.kaferest.presentation.shop.menu.home.viewmodel

import android.net.Uri

sealed class ShopCreationEvent {
    data class UpdateShopName(
        val shopName: String = ""
    ): ShopCreationEvent()

    data class UpdateShopAdress(
        val shopAddress: String = ""
    ): ShopCreationEvent()

    data class UpdateShopPhotos(
        val shopPhotos: List<Uri>
    ): ShopCreationEvent()

}
