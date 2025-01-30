package com.example.kaferest.presentation.shop.menu.home.viewmodel

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.example.kaferest.presentation.shop.menu.home.model.Product

@Immutable
data class ShopCreationState(
    val shopName: String = "",
    val shopAddress: String = "",
    val photos: List<Uri> = emptyList(),
    val categories: List<String> = emptyList(),
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCreated: Boolean = false
)