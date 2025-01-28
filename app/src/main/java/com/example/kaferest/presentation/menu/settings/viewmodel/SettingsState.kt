package com.example.kaferest.presentation.menu.settings.viewmodel

data class SettingsState(
    val isDarkMode: Boolean = false,
    val selectedLanguage: String = "English",
    val isLoading: Boolean = false,
    val isSignedOut:Boolean = false,
    val userEmail: String = "",
    val userName: String = "",
    val error: String ="",
    val notificationsEnabled: Boolean = true
)

