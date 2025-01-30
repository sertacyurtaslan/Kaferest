package com.example.kaferest.domain.model

data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val photoUri: String = "",
    val isAvailable: Boolean = true,
    val shopId: String = "",
    val ingredients: List<String> = emptyList(),
    val calories: Int = 0,
    val discountPercentage: Int = 0,
)