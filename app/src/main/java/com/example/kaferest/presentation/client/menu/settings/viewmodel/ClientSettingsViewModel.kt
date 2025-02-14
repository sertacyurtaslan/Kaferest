package com.example.kaferest.presentation.client.menu.settings.viewmodel

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
class ClientSettingsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userManager: UserManager,
    private val themePreferences: ThemePreferences,
) : ViewModel() {

    private val _settingsState = MutableStateFlow(ClientSettingsState())
    val settingsState: StateFlow<ClientSettingsState> = _settingsState.asStateFlow()

    init {
        loadUserData()
        loadThemePreference()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val currentUser = firebaseAuth.currentUser
                currentUser?.let { user ->
                    _settingsState.value = _settingsState.value.copy(
                        userName = user.displayName ?: "",
                        userEmail = user.email ?: ""
                    )
                }
            } catch (e: Exception) {
                _settingsState.value = _settingsState.value.copy(
                    error = e.message ?: "Error loading user data"
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

    fun toggleNotifications() {
        _settingsState.value = _settingsState.value.copy(
            notificationsEnabled = !_settingsState.value.notificationsEnabled
        )
    }


    fun signOut() {
        viewModelScope.launch {
            try {
                userManager.clearUser()
                firebaseAuth.signOut()
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