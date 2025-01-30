package com.example.kaferest.presentation.shop.menu.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kaferest.R

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) {
    data object Shop : BottomNavItem("shops", Icons.Default.Home, R.string.nav_shop)
    data object Empty : BottomNavItem("empty", Icons.Default.Home, 0) // Placeholder for FAB
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, R.string.nav_settings)
}