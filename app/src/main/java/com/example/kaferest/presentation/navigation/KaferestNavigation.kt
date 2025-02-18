package com.example.kaferest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaferest.presentation.shop.nav.ShopMainScreen
import com.example.kaferest.presentation.client.nav.ui.ClientMainScreen
import com.example.kaferest.presentation.auth.menu.register.viewmodel.RegisterViewModel
import com.example.kaferest.presentation.navigation.NavigationAnimation.enterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.exitTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popEnterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popExitTransition
import com.example.kaferest.presentation.splash.ui.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.example.kaferest.presentation.shop.menu.shop_creation.ui.ShopCreationScreen
import com.example.kaferest.presentation.auth.menu.intro.ui.IntroScreen
import com.example.kaferest.presentation.auth.menu.login.ui.LoginScreen
import com.example.kaferest.presentation.auth.register.ui.EmailVerificationScreen
import com.example.kaferest.presentation.auth.menu.shop_login.ui.ShopLoginScreen
import com.example.kaferest.presentation.entrance.forgot_password.ui.ForgotPasswordScreen
import com.example.kaferest.presentation.entrance.register.ui.RegisterScreen

@Composable
fun KaferestNavigation() {
    val navController = rememberNavController()
    val sharedViewModel: RegisterViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        //Splash
        slideComposable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }

        //Auth
        slideComposable(Screen.IntroScreen.route) { IntroScreen(navController) }
        slideComposable(Screen.LoginScreen.route) { LoginScreen(navController) }
        slideComposable(Screen.ShopLoginScreen.route) { ShopLoginScreen(navController) }
        slideComposable(Screen.ForgotPasswordScreen.route) { ForgotPasswordScreen(navController) }
        slideComposable(Screen.RegisterScreen.route) { RegisterScreen(navController, sharedViewModel) }
        slideComposable(Screen.EmailVerificationScreen.route) { EmailVerificationScreen(navController, sharedViewModel) }

        // Client Navigation Graph
        slideComposable(Screen.ClientMainScreen.route) {
            ClientMainScreen(navController)
        }

        //ShopCreation
        slideComposable(Screen.ShopCreationScreen.route) {
            ShopCreationScreen(navController)
        }

        // Shop Navigation Graph
        slideComposable(Screen.ShopMainScreen.route) {
            ShopMainScreen(navController)
        }
    }
}

fun NavGraphBuilder.slideComposable(
    route: String,
    content: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        content()
    }
}