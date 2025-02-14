package com.example.kaferest.presentation.menu.qr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.presentation.shop.menu.qr_scan.viewmodel.ShopQrScanState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopQrScanViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(ShopQrScanState())
    val state: StateFlow<ShopQrScanState> = _state.asStateFlow()

    init {
        loadUserId()
    }

    private fun loadUserId() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val currentUser = firebaseAuth.currentUser
                _state.value = _state.value.copy(
                    userId = currentUser?.uid ?: "",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Error loading user ID",
                    isLoading = false
                )
            }
        }
    }

    fun refreshUserId() {
        loadUserId()
    }
} 