package com.example.kaferest.presentation.splash.viewmodel

data class SplashScreenState(
    val isUserSignedIn: Boolean = false,
    val isAdminSignedIn: Boolean = false,
    val isAdminInitialized: Boolean = false,
    val isUserInitialized: Boolean = false
) 