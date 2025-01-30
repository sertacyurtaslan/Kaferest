package com.example.kaferest.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {
    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.asStateFlow()

    init {
        checkSignInStatus()
    }

    private fun checkSignInStatus() {
        viewModelScope.launch {
            // Check user status
            userManager.user.collect { user ->
                _state.update { currentState ->
                    currentState.copy(
                        isUserSignedIn = user != null,
                        isUserInitialized = true
                    )
                }
            }
        }

        viewModelScope.launch {
            // Check admin status
            userManager.admin.collect { admin ->
                _state.update { currentState ->
                    currentState.copy(
                        isAdminSignedIn = admin != null,
                        isAdminInitialized = true
                    )
                }
            }
        }
    }
} 