package com.example.kaferest.presentation.menu.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kaferest.R

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) {
    data object Home : BottomNavItem("home", Icons.Default.Home, R.string.nav_home)
    data object Shops : BottomNavItem("shops", Icons.Default.Search, R.string.nav_shops)
    data object Empty : BottomNavItem("empty", Icons.Default.Home, 0) // Placeholder for FAB
    data object Games : BottomNavItem("games", Icons.Default.Star, R.string.nav_games)
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, R.string.nav_settings)
}