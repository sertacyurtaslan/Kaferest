package com.example.kaferest.presentation.shop.menu.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.Product
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
        _state.value = _state.value.copy(photos = photos)
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

    private suspend fun uploadPhotos(photos: List<Uri>): List<Uri> {
        return photos.map { photoUri ->
            uploadPhoto(photoUri)
        }
    }

    fun addCategory(category: String) {
        val currentCategories = _state.value.categories.toMutableList()
        if (!currentCategories.contains(category)) {
            currentCategories.add(category)
            _state.value = _state.value.copy(categories = currentCategories)
        }
    }

    fun removeCategory(category: String) {
        val currentCategories = _state.value.categories.toMutableList()
        currentCategories.remove(category)
        _state.value = _state.value.copy(categories = currentCategories)
    }

    fun addProduct(product: Product) {
        val currentProducts = _state.value.products.toMutableList()
        currentProducts.add(product)
        _state.value = _state.value.copy(products = currentProducts)
    }

    fun removeProduct(product: Product) {
        val currentProducts = _state.value.products.toMutableList()
        currentProducts.remove(product)
        _state.value = _state.value.copy(products = currentProducts)
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
            val uploadedPhotoUris = uploadPhotos(_state.value.photos)

            val shop = hashMapOf(
                "shopName" to _state.value.shopName,
                "shopAddress" to _state.value.shopAddress,
                "shopPhotos" to uploadedPhotoUris,
                "shopCategories" to _state.value.categories,
                "shopProducts" to _state.value.products.map { product ->
                    hashMapOf(
                        "productName" to product.name,
                        "productPrice" to product.price,
                        "productCategory" to product.category,
                        "productPhotoUri" to product.photoUri
                    )
                }
            )

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

