package com.example.kaferest.presentation.shop.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kaferest.R

sealed class ShopBottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) {
    data object ShopHomeScreen : ShopBottomNavItem("shop_home_screen", Icons.Default.Home, R.string.nav_shop)
    data object Empty : ShopBottomNavItem("empty", Icons.Default.Home, 0)
    data object ShopSettingsScreen : ShopBottomNavItem("shop_settings_screen", Icons.Default.Settings, R.string.nav_settings)
}