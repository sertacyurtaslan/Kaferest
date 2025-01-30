package com.example.kaferest.presentation.shop.menu.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.example.kaferest.presentation.shop.menu.home.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class ShopCreationViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _state = MutableStateFlow(ShopCreationState())
    val state: StateFlow<ShopCreationState> = _state.asStateFlow()

    private fun updateShopName(name: String) {
        _state.value = _state.value.copy(shopName = name)
    }

    private fun updateShopAddress(address: String) {
        _state.value = _state.value.copy(shopAddress = address)
    }

    private suspend fun uploadPhoto(photoUri: Uri): String {
        val fileName = "owners/${auth.currentUser?.uid}/photos/${UUID.randomUUID()}"
        val photoRef = storage.reference.child(fileName)

        return try {
            photoRef.putFile(photoUri).await()
            photoRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadPhotos(photos: List<Uri>): List<String> {
        return photos.map { photoUri ->
            uploadPhoto(photoUri)
        }
    }

    fun onEvent(event: ShopCreationEvent) {
        when (event) {
            is ShopCreationEvent.UpdateShopName -> {
                updateShopName(event.shopName)
            }
            is ShopCreationEvent.UpdateShopAdress -> {
                updateShopAddress(event.shopAddress)
            }
            is ShopCreationEvent.UploadPhotos -> {

            }
        }
    }



    fun requestLocation() {
        // TODO: Implement location permission request and get current location
    }


    fun validatePhotos(): Boolean {
        return state.value.photos.isNotEmpty()
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
            val uploadedPhotoUrls = uploadPhotos(_state.value.photos)

            val shop = hashMapOf(
                "ownerId" to currentUser.uid,
                "name" to _state.value.shopName,
                "address" to _state.value.shopAddress,
                "photos" to uploadedPhotoUrls,
                "categories" to _state.value.categories,
                "products" to _state.value.products.map { product ->
                    hashMapOf(
                        "name" to product.name,
                        "price" to product.price,
                        "category" to product.category,
                        "photoUrl" to product.photoUrl
                    )
                }
            )

            firestore.collection("shops")
                .add(shop)
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
}

