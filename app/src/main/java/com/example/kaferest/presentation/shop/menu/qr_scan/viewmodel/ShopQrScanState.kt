package com.example.kaferest.presentation.shop.menu.qr_scan.viewmodel

data class ShopQrScanState(
    val userId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)