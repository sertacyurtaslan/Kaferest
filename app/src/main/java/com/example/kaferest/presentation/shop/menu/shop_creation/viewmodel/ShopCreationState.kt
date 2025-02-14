package com.example.kaferest.presentation.admin.menu.shop_creation.viewmodel

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.example.kaferest.domain.model.Category

@Immutable
data class ShopCreationState(
    val shopName: String = "",
    val shopAddress: String = "",
    val shopPhotos: List<Uri> = emptyList(),
    val shopCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCreated: Boolean = false
)