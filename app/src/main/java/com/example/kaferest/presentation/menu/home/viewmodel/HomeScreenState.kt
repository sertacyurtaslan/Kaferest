package com.example.kaferest.presentation.menu.home.viewmodel

import androidx.compose.runtime.Immutable
import com.example.kaferest.domain.model.Shop

@Immutable
data class HomeScreenState(
    val shops: List<Shop> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val selectedShop: Shop? = null
)
