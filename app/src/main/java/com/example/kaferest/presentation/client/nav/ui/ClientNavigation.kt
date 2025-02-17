package com.example.kaferest.presentation.client.nav.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kaferest.presentation.navigation.Screen
import com.example.kaferest.presentation.client.menu.games.ui.ClientGamesScreen
import com.example.kaferest.presentation.client.menu.home.ui.ClientHomeScreen
import com.example.kaferest.presentation.client.menu.settings.ui.ClientSettingsScreen
import com.example.kaferest.presentation.client.nav.viewmodel.ClientMainViewModel
import com.example.kaferest.presentation.menu.shops.ui.ClientShopsScreen
import com.example.kaferest.presentation.client.menu.shops.ui.ShopDetailScreen
import com.example.kaferest.presentation.client.menu.qr.ui.ClientQrScreen

@Composable
fun ClientNavigation(
    navController: NavHostController,
    rootNavController: NavController
) {
    val clientMainViewModel: ClientMainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ClientHomeScreen.route
    ) {
        composable(Screen.ClientHomeScreen.route) {
            ClientHomeScreen(navController, clientMainViewModel)
        }

        composable(Screen.ClientShopsScreen.route) {
            ClientShopsScreen(navController, clientMainViewModel)
        }

        composable(Screen.ClientQrScreen.route) {
            ClientQrScreen(navController, clientMainViewModel)
        }

        composable(Screen.ClientGamesScreen.route) {
            ClientGamesScreen(navController)
        }

        composable(Screen.ClientSettingsScreen.route) {
            ClientSettingsScreen(rootNavController)
        }

        // Shop detail uses its own back navigation
        composable(
            route = "client_shop_detail_screen/{shopId}",
            arguments = listOf(navArgument("shopId") { type = NavType.StringType })
        ) {
            ShopDetailScreen(navController, clientMainViewModel)
        }
    }
} 