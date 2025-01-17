package com.example.kaferest.presentation.navigation

sealed class Screen(val route: String){
    data object IntroScreen: Screen(route = "intro_screen")
    data object LoginScreen: Screen(route = "login_screen")
    data object RegisterScreen: Screen(route = "register_screen")
    data object ForgotPasswordScreen: Screen(route = "forgot_password_screen")
    data object AdminLoginScreen: Screen(route = "admin_login_screen")
    data object HomeScreen: Screen(route = "home_screen")
    data object SettingsScreen: Screen(route = "settings_screen")
    data object MainScreenWithNavBar: Screen(route = "main_screen_with_navbar")
    data object EmailVerificationScreen: Screen(route = "email_verification")
}