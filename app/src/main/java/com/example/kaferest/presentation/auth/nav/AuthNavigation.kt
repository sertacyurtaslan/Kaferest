package com.example.kaferest.presentation.auth.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import com.example.kaferest.presentation.auth.menu.intro.ui.IntroScreen
import com.example.kaferest.presentation.auth.menu.login.ui.LoginScreen
import com.example.kaferest.presentation.entrance.register.ui.RegisterScreen
import com.example.kaferest.presentation.entrance.admin_login.ui.AdminLoginScreen
import com.example.kaferest.presentation.entrance.forgot_password.ui.ForgotPasswordScreen
import com.example.kaferest.presentation.auth.register.ui.EmailVerificationScreen
import com.example.kaferest.presentation.auth.menu.register.viewmodel.RegisterViewModel
import com.example.kaferest.presentation.navigation.NavigationAnimation.enterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.exitTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popEnterTransition
import com.example.kaferest.presentation.navigation.NavigationAnimation.popExitTransition
import com.example.kaferest.presentation.navigation.Screen

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    sharedViewModel: RegisterViewModel
) {
    navigation(
        startDestination = Screen.IntroScreen.route,
        route = Screen.AuthGraph.route
    ) {
        // Intro Screen
        composable(
            route = Screen.IntroScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            IntroScreen(navController)
        }

        // Login Screen
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            LoginScreen(navController)
        }

        // Register Screen
        composable(
            route = Screen.RegisterScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            RegisterScreen(navController, sharedViewModel)
        }

        // Email Verification Screen
        composable(
            route = Screen.EmailVerificationScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            EmailVerificationScreen(navController, sharedViewModel)
        }

        // Admin Login Screen
        composable(
            route = Screen.AdminLoginScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            AdminLoginScreen(navController)
        }

        // Forgot Password Screen
        composable(
            route = Screen.ForgotPasswordScreen.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            ForgotPasswordScreen(navController)
        }
    }
} 