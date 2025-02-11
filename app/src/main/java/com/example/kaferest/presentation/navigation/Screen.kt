package com.example.kaferest.presentation.navigation

sealed class Screen(val route: String){
    data object SplashScreen: Screen(route = "splash_screen")
    data object IntroScreen: Screen(route = "intro_screen")
    data object LoginScreen: Screen(route = "login_screen")
    data object RegisterScreen: Screen(route = "register_screen")
    data object ForgotPasswordScreen: Screen(route = "forgot_password_screen")
    data object AdminLoginScreen: Screen(route = "admin_login_screen")
    data object EmailVerificationScreen: Screen(route = "email_verification")
    data object ShopsScreen : Screen("shops_screen")
    data object MainScreen : Screen("main_screen")
    data object Home : Screen("home")
    data object ShopDetail : Screen("shop/{shopId}") {
        fun createRoute(shopId: String) = "shop/$shopId"
    }
    data object AdminHomeScreen: Screen(route = "admin_home_screen")
    data object AdminMainScreen: Screen(route = "admin_main_screen")
    data object ShopCreationScreen: Screen(route = "shop_creation_screen")

}