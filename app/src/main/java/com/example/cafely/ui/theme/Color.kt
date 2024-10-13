package com.example.cafely.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Coffee = Color(0xFF674334)
val CoffeeDark = Color(0xFF422B23)
val CoffeeBlack = Color(0xFF140E0B)


val CoffeeGradient: Brush = Brush.linearGradient(
    colors = listOf(
        Coffee,
        CoffeeBlack
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY)
)