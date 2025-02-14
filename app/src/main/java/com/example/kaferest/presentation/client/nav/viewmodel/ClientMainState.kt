package com.example.kaferest.presentation.client.nav.viewmodel

import com.example.kaferest.domain.model.Shop
import com.example.kaferest.domain.model.User

data class ClientMainState(
    val shops: List<Shop> = emptyList(),
    val user: User? = null,
    val selectedShop: Shop? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = true,
    val error: String = ""
) 