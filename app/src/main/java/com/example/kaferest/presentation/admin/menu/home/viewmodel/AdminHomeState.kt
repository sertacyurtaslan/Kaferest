package com.example.kaferest.presentation.admin.menu.home.viewmodel

import android.net.Uri
import com.example.kaferest.domain.model.Shop

data class AdminHomeState(
    val shop: Shop? = null,
    val shopImagesUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)