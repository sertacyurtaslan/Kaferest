package com.example.kaferest.presentation.admin.menu.shop_creation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.Category
import com.example.kaferest.domain.model.Product
import com.example.kaferest.domain.model.Shop
import com.example.kaferest.util.CurrentDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class ShopCreationViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val userManager: UserManager,
    ) : ViewModel() {

    private val _state = MutableStateFlow(ShopCreationState())
    val state: StateFlow<ShopCreationState> = _state.asStateFlow()

    private fun updateShopName(name: String) {
        _state.value = _state.value.copy(shopName = name)
    }

    private fun updateShopAddress(address: String) {
        _state.value = _state.value.copy(shopAddress = address)
    }

    private fun updatePhotos(photos: List<Uri>) {
        _state.value = _state.value.copy(shopPhotos = photos)
    }

    private suspend fun uploadPhoto(photoUri: Uri): Uri {
        val userId = userManager.admin.firstOrNull()?.userId ?: ""
        val fileName = "shops/${userId}/shopPhotos/${UUID.randomUUID()}"
        val photoRef = storage.reference.child(fileName)

        return try {
            photoRef.putFile(photoUri).await()
            photoRef.downloadUrl.await()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun uploadPhotos(photos: List<Uri>): List<String> {
        return photos.map { uri ->
            // Her bir fotoğrafı yükle ve download URL'ini al
            val storageRef = storage.reference.child("shops/${UUID.randomUUID()}")
            storageRef.putFile(uri).await() // Yüklemeyi bekle
            storageRef.downloadUrl.await().toString() // Download URL'i string olarak al
        }
    }

    fun addCategory(category: Category) {
        val currentCategories = _state.value.shopCategories.toMutableList()

        if (!currentCategories.any { it.categoryName == category.categoryName}) {
            currentCategories.add(category)
            _state.value = _state.value.copy(shopCategories = currentCategories)
        }
    }

    fun removeCategory(categoryName: String) {
        val currentCategories = _state.value.shopCategories.toMutableList()
        currentCategories.removeAll { it.categoryName == categoryName }
        _state.value = _state.value.copy(shopCategories = currentCategories)
    }

    fun addProduct(product: Product, categoryName: String) {
        val currentCategories = _state.value.shopCategories.toMutableList()
        val categoryIndex = currentCategories.indexOfFirst { it.categoryName == categoryName }
        
        if (categoryIndex != -1) {
            val category = currentCategories[categoryIndex]
            val updatedProducts = category.categoryProducts.toMutableList().apply {
                add(product)
            }
            currentCategories[categoryIndex] = category.copy(categoryProducts = updatedProducts)
            _state.value = _state.value.copy(shopCategories = currentCategories)
        }
    }

    fun removeProduct(product: Product, categoryName: String) {
        val currentCategories = _state.value.shopCategories.toMutableList()
        val categoryIndex = currentCategories.indexOfFirst { it.categoryName == categoryName }
        
        if (categoryIndex != -1) {
            val category = currentCategories[categoryIndex]
            val updatedProducts = category.categoryProducts.toMutableList().apply {
                remove(product)
            }
            currentCategories[categoryIndex] = category.copy(categoryProducts = updatedProducts)
            _state.value = _state.value.copy(shopCategories = currentCategories)
        }
    }

    fun createShop() = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _state.value = _state.value.copy(
                    error = "User not authenticated",
                    isLoading = false
                )
                return@launch
            }

            // Upload photos first
            val uploadedPhotoUris = uploadPhotos(_state.value.shopPhotos)

            val shop = Shop(
                shopName = _state.value.shopName,
                shopMail = currentUser.email.toString(),
                shopAddress = _state.value.shopAddress,
                shopImages = uploadedPhotoUris,
                shopCategories = _state.value.shopCategories,
                shopCreationDate = CurrentDate().getFormattedDate()
            )

            userManager.saveAdmin(shop)

            firestore.collection("shops")
                .document(currentUser.uid)
                .set(shop)
                .addOnSuccessListener {
                    _state.value = _state.value.copy(
                        isCreated = true,
                        isLoading = false
                    )
                }
                .addOnFailureListener { e ->
                    _state.value = _state.value.copy(
                        error = e.message ?: "Failed to create shop",
                        isLoading = false
                    )
                }
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.message ?: "An error occurred",
                isLoading = false
            )
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }

    fun onEvent(event: ShopCreationEvent) {
        when (event) {
            is ShopCreationEvent.UpdateShopName -> {
                updateShopName(event.shopName)
            }
            is ShopCreationEvent.UpdateShopAdress -> {
                updateShopAddress(event.shopAddress)
            }
            is ShopCreationEvent.UpdateShopPhotos -> {
                updatePhotos(event.shopPhotos)
            }

        }
    }
}

