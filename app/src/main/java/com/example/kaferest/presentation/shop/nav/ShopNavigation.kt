package com.example.kaferest.presentation.shop.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaferest.presentation.admin.menu.home.ui.ShopHomeScreen
import com.example.kaferest.presentation.shop.menu.settings.ui.ShopSettingsScreen
import com.example.kaferest.presentation.navigation.NavigationAnimation.enterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.exitTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popEnterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popExitTransition
import com.example.kaferest.presentation.navigation.Screen

@Composable
fun ShopNavigation(
    navController: NavHostController,
    rootNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ShopHomeScreen.route,
        route = Screen.ShopGraph.route
    ) {
        composable(
            route = Screen.ShopHomeScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            ShopHomeScreen(navController)
        }

        composable(
            route = Screen.ShopSettingsScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            ShopSettingsScreen(rootNavController)
        }
    }
} 