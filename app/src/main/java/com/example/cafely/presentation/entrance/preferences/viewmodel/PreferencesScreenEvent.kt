package com.example.financecompose.presentation.entrance.preferences.viewmodel

sealed class PreferencesScreenEvent {
    data class SavePreferences(
        val userBalance: String,
        val userCurrency: String
    ) : PreferencesScreenEvent()
}
