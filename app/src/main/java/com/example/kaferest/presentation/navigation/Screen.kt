package com.example.kaferest.presentation.navigation

sealed class Screen(val route: String) {
    //Splash
    data object SplashScreen : Screen("splash_screen")

    // Auth Graph
    data object AuthGraph : Screen("auth_graph")
    data object IntroScreen : Screen("intro_screen")
    data object LoginScreen : Screen("login_screen")
    data object RegisterScreen : Screen("register_screen")
    data object ShopLoginScreen : Screen("shop_login_screen")
    data object ForgotPasswordScreen : Screen("forgot_password_screen")
    data object EmailVerificationScreen : Screen("email_verification_screen")
    
    // Client Graph (Nav Bar)
    data object ClientGraph : Screen("client_graph")
    data object ClientMainScreen : Screen("client_main_screen")
    data object ClientHomeScreen : Screen("client_home_screen")
    data object ClientShopsScreen : Screen("client_shops_screen")
    data object ClientGamesScreen : Screen("client_games_screen")
    data object ClientQrScreen : Screen("client_qr_screen")
    data object ClientSettingsScreen : Screen("client_settings_screen")
    data object ClientShopDetailScreen : Screen("client_shop_detail_screen/{shopId}") {
        fun createRoute(shopId: String) = "client_shop_detail_screen/$shopId"
    }

    // Shop Graph (Nav Bar)
    data object ShopGraph : Screen("shop_graph")
    data object ShopCreationScreen : Screen("shop_creation_screen")
    data object ShopMainScreen : Screen("shop_main_screen")
    data object ShopHomeScreen : Screen("shop_home_screen")
    data object ShopQrScanScreen : Screen("shop_qr_scan_screen")
    data object ShopSettingsScreen : Screen("shop_settings_screen")
}

