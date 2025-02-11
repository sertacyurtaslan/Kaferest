package com.example.kaferest.domain.model

data class Shop(
    val shopName: String = "",
    val shopMail: String = "",
    val shopAddress: String = "",
    val shopRating: Double = 0.0,
    val shopImages: List<String> = emptyList(),
    val shopCategories: List<Category> = emptyList(),
    val shopCreationDate: String = ""
)

