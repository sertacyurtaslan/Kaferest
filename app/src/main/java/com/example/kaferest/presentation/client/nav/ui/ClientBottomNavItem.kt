package com.example.kaferest.presentation.client.nav.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kaferest.R

sealed class ClientBottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) {
    data object ClientHomeScreen : ClientBottomNavItem("client_home_screen", Icons.Default.Home, R.string.nav_home)
    data object ClientShopsScreen : ClientBottomNavItem("client_shops_screen", Icons.Default.Search, R.string.nav_shops)
    data object Empty : ClientBottomNavItem("empty", Icons.Default.Home, 0) // Placeholder for FAB
    data object ClientGamesScreen : ClientBottomNavItem("client_games_screen", Icons.Default.Star, R.string.nav_games)
    data object ClientSettingsScreen : ClientBottomNavItem("client_settings_screen", Icons.Default.Settings, R.string.nav_settings)
}