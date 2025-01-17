package com.example.kaferest.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaferest.presentation.entrance.forgot_password.ui.ForgotPasswordScreen
import com.example.kaferest.presentation.entrance.admin_login.ui.AdminLoginScreen
import com.example.kaferest.presentation.entrance.intro.ui.IntroScreen
import com.example.kaferest.presentation.entrance.login.ui.LoginScreen
import com.example.kaferest.presentation.entrance.register.ui.RegisterScreen
import com.example.kaferest.presentation.entrance.register.ui.EmailVerificationScreen
import com.example.kaferest.presentation.entrance.register.viewmodel.RegisterViewModel
import com.example.kaferest.presentation.menu.home.ui.HomeScreen

@Composable
fun ScreensNavigation(
) {
    val navController = rememberNavController()
    val sharedViewModel : RegisterViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.IntroScreen.route
    ) {
        composable(Screen.IntroScreen.route) { IntroScreen(navController) }
        addComposable(Screen.LoginScreen.route, navController) { LoginScreen(navController) }
        addComposable(Screen.AdminLoginScreen.route, navController) { AdminLoginScreen(navController) }
        addComposable(Screen.ForgotPasswordScreen.route, navController) { ForgotPasswordScreen(navController) }
        addComposable(Screen.RegisterScreen.route, navController) { RegisterScreen(navController, sharedViewModel) }
        addComposable(Screen.EmailVerificationScreen.route, navController) { EmailVerificationScreen(navController, sharedViewModel) }
        addComposable(Screen.HomeScreen.route, navController) { HomeScreen(navController) }
    }
}

fun NavGraphBuilder.addComposable(route: String, navController: NavController, content: @Composable () -> Unit) {
    composable(
        route = route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(
                    300,
                    easing = FastOutLinearInEasing
                )
            )
        }
    ) {
        content()
    }
}



