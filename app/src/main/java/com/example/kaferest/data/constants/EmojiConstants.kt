package com.example.kaferest.data.constants

object EmojiConstants {
    // Food & Drinks
    val FOOD_AND_DRINKS = listOf(
        "☕", "🔥", "❄️", "🍰", "🎂", "🍪", "🍳", "🥪", "🥗", "🍛",
        "🍔", "🍝", "🍓", "🍹", "🥤", "🍵", "🧉", "🌱", "🧒", "🌟"
    )

    // Additional Food
    val ADDITIONAL_FOOD = listOf(
        "🥐", "🥯", "🥖", "🥨", "🥞", "🧇", "🧀", "🥩", "🥓", "🍖",
        "🍗", "🥚", "🍜", "🍲", "🥣", "🥡", "🥠", "🍱", "🍘", "🍙"
    )

    // Drinks & Desserts
    val DRINKS_AND_DESSERTS = listOf(
        "🍺", "🍷", "🍸", "🍾", "🥂", "🧃", "🧊", "🍶", "🥛", "🍼",
        "🍮", "🍯", "🍬", "🍭", "🍫", "🍿", "🥜", "🌰", "🥮", "🍡"
    )

    // Fruits & Vegetables
    val FRUITS_AND_VEGETABLES = listOf(
        "🍎", "🍐", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🫐", "🥝",
        "🍅", "🥑", "🥦", "🥬", "🥒", "🌶️", "🫑", "🧄", "🧅", "🥕"
    )

    // All emojis combined
    val ALL_EMOJIS = FOOD_AND_DRINKS + ADDITIONAL_FOOD + DRINKS_AND_DESSERTS + FRUITS_AND_VEGETABLES
} 