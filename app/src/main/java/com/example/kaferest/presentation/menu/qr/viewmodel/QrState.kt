package com.example.kaferest.presentation.menu.qr.viewmodel

data class QrState(
    val userId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)