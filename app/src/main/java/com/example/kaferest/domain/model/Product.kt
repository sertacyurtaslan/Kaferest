package com.example.kaferest.domain.model

data class Product(
    val productId: String = "",
    val productName: String = "",
    val productPrice: Double = 0.0,
    val productCategory: String = "",
    val productPhotoUri: String = "",
    val productAvailability: Boolean = true,
    val productDiscountPercentage: Double = 0.0,
)