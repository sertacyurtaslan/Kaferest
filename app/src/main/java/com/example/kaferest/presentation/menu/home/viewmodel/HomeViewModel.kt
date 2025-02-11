package com.example.kaferest.presentation.menu.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.model.Shop
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadShops()
    }

    private fun loadShops() = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            
            // Fetch all shops from Firestore
            val shopsSnapshot = firestore.collection("shops").get().await()
            val shops = shopsSnapshot.documents.mapNotNull { doc ->
                doc.toObject(Shop::class.java)
            }
            
            _state.value = _state.value.copy(
                shops = shops,
                isLoading = false
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.message ?: "Failed to load shops",
                isLoading = false
            )
        }
    }

    fun selectShop(shop: Shop) {
        _state.value = _state.value.copy(selectedShop = shop)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
}
