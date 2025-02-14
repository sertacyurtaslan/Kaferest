package com.example.kaferest.presentation.client.nav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.model.Shop
import com.example.kaferest.domain.model.User
import com.example.kaferest.domain.repository.KaferestRepository
import com.example.kaferest.domain.manager.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ClientMainViewModel @Inject constructor(
    private val kaferestRepository: KaferestRepository,
    private val userManager: UserManager
) : ViewModel() {

    private val _state = MutableStateFlow(ClientMainState())
    val state: StateFlow<ClientMainState> = _state.asStateFlow()

    init {
        loadUser()
        loadShops()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                userManager.user.collect { user ->
                    _state.value = _state.value.copy(
                        user = user,
                        isLoading = false,
                        isConnected = true
                    )
                }
            } catch (e: IOException) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isConnected = false,
                    error = e.message ?: "Connection error"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun loadShops() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val shops = kaferestRepository.getShops()
                _state.value = _state.value.copy(
                    shops = shops,
                    isLoading = false,
                    isConnected = true
                )

            } catch (e: IOException) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isConnected = false,
                    error = e.message ?: "Connection error"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun selectShop(shop: Shop) {
        _state.value = _state.value.copy(selectedShop = shop)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
} 