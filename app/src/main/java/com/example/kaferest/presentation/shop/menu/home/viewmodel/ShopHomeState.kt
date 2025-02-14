package com.example.kaferest.presentation.shop.menu.home.viewmodel

import android.net.Uri
import com.example.kaferest.domain.model.Shop

data class ShopHomeState(
    val shop: Shop? = null,
    val shopImagesUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)