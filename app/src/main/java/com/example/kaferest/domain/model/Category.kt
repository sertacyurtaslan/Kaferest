package com.example.kaferest.domain.model

data class Category(
    val categoryId: String = "",
    val categoryName: String = "",
    val categoryEmoji: String = "",
    val categoryProducts: List<Product> = emptyList()
)