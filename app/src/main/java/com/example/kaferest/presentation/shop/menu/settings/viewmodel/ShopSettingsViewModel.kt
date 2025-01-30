package com.example.kaferest.presentation.shop.menu.settings.viewmodel

import com.example.kaferest.presentation.menu.settings.viewmodel.SettingsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.data.preferences.ThemePreferences
import com.example.kaferest.domain.manager.UserManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopSettingsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userManager: UserManager,
    private val themePreferences: ThemePreferences,
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        loadUserData()
        loadThemePreference()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                // Collect admin data from UserManager
                userManager.admin.collect { admin ->
                    admin?.let {
                        _settingsState.value = _settingsState.value.copy(
                            userName = it.userName ?: "",
                            userEmail = it.userEmail ?: ""
                        )
                    }
                }
            } catch (e: Exception) {
                _settingsState.value = _settingsState.value.copy(
                    error = e.message ?: "Error loading admin data"
                )
            }
        }
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            themePreferences.isDarkMode.collect { isDarkMode ->
                _settingsState.value = _settingsState.value.copy(
                    isDarkMode = isDarkMode
                )
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newDarkMode = !_settingsState.value.isDarkMode
            themePreferences.setDarkMode(newDarkMode)
        }
    }

    fun updateLanguage(language: String) {
        _settingsState.value = _settingsState.value.copy(
            selectedLanguage = language
        )
    }

    fun toggleNotifications() {
        _settingsState.value = _settingsState.value.copy(
            notificationsEnabled = !_settingsState.value.notificationsEnabled
        )
    }

    fun signOutAdmin() {
        viewModelScope.launch {
            try {
                userManager.clearAdmin()
                _settingsState.value = _settingsState.value.copy(
                    isSignedOut = true
                )
            } catch (e: Exception) {
                _settingsState.value = _settingsState.value.copy(
                    error = e.message ?: "Error signing out"
                )
            }
        }
    }
}