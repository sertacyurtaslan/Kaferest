package com.example.kaferest.presentation.admin.menu.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.Category
import com.example.kaferest.domain.model.Product
import com.example.kaferest.domain.model.Shop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : ViewModel() {

    private val _state = MutableStateFlow(AdminHomeState(isLoading = true))
    val state: StateFlow<AdminHomeState> = _state.asStateFlow()

    init {
        loadShopData()
    }

    private fun loadShopData() = viewModelScope.launch {
        try {
            val userId = auth.currentUser?.uid ?: return@launch
            
            // Get shop data from Firestore
            val shopDoc = firestore.collection("shops").document(userId).get().await()
            val shop = shopDoc.toObject(Shop::class.java)

            // Convert string URLs to Uri objects for UI
            val imageUris = shop?.shopImages?.map { imageUrl ->
                Uri.parse(imageUrl)
            } ?: emptyList()

            // Update state with both shop data and converted URIs
            _state.value = _state.value.copy(
                shop = shop,
                shopImagesUris = imageUris,
                isLoading = false
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.message ?: "Failed to load shop data",
                isLoading = false
            )
        }
    }

    fun updateShopName(name: String) = viewModelScope.launch {
        updateShopField("shopName", name)
    }

    fun updateShopAddress(address: String) = viewModelScope.launch {
        updateShopField("shopAddress", address)
    }

    fun addShopImage(imageUri: Uri) = viewModelScope.launch {
        try {
            val userId = auth.currentUser?.uid ?: return@launch
            val fileName = "shops/$userId/shopPhotos/${UUID.randomUUID()}"
            val photoRef = storage.reference.child(fileName)
            
            photoRef.putFile(imageUri).await()
            val downloadUrl = photoRef.downloadUrl.await().toString()
            
            val currentImages = _state.value.shop?.shopImages?.toMutableList() ?: mutableListOf()
            currentImages.add(downloadUrl)
            
            updateShopField("shopImages", currentImages)

            // Update UI state with new Uri
            val currentUris = _state.value.shopImagesUris.toMutableList()
            currentUris.add(imageUri)
            _state.value = _state.value.copy(shopImagesUris = currentUris)
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = e.message ?: "Failed to add image")
        }
    }

    fun removeShopImage(imageUri: Uri) = viewModelScope.launch {
        try {
            val shop = _state.value.shop ?: return@launch
            val imageUrl = imageUri.toString()
            
            val currentImages = shop.shopImages.toMutableList()
            currentImages.remove(imageUrl)
            
            updateShopField("shopImages", currentImages)

            // Update UI state
            val currentUris = _state.value.shopImagesUris.toMutableList()
            currentUris.remove(imageUri)
            _state.value = _state.value.copy(shopImagesUris = currentUris)
            
            // Delete from storage if possible
            try {
                storage.getReferenceFromUrl(imageUrl).delete()
            } catch (e: Exception) {
                // Ignore storage deletion errors
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = e.message ?: "Failed to remove image")
        }
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        val currentCategories = _state.value.shop?.shopCategories?.toMutableList() ?: return@launch
        val index = currentCategories.indexOfFirst { it.categoryId == category.categoryId }
        if (index != -1) {
            currentCategories[index] = category
            updateShopField("shopCategories", currentCategories)
        }
    }

    fun removeCategory(category: Category) = viewModelScope.launch {
        val currentCategories = _state.value.shop?.shopCategories?.toMutableList() ?: return@launch
        currentCategories.removeAll { it.categoryId == category.categoryId }
        updateShopField("shopCategories", currentCategories)
    }

    fun addProduct(product: Product, categoryName: String) = viewModelScope.launch {
        val currentCategories = _state.value.shop?.shopCategories?.toMutableList() ?: return@launch
        val categoryIndex = currentCategories.indexOfFirst { it.categoryName == categoryName }
        
        if (categoryIndex != -1) {
            val category = currentCategories[categoryIndex]
            val updatedProducts = category.categoryProducts.toMutableList().apply {
                add(product)
            }
            currentCategories[categoryIndex] = category.copy(categoryProducts = updatedProducts)
            updateShopField("shopCategories", currentCategories)
        }
    }

    fun updateProduct(product: Product, categoryName: String) = viewModelScope.launch {
        val currentCategories = _state.value.shop?.shopCategories?.toMutableList() ?: return@launch
        val categoryIndex = currentCategories.indexOfFirst { it.categoryName == categoryName }
        
        if (categoryIndex != -1) {
            val category = currentCategories[categoryIndex]
            val productIndex = category.categoryProducts.indexOfFirst { it.productId == product.productId }
            
            if (productIndex != -1) {
                val updatedProducts = category.categoryProducts.toMutableList().apply {
                    set(productIndex, product)
                }
                currentCategories[categoryIndex] = category.copy(categoryProducts = updatedProducts)
                updateShopField("shopCategories", currentCategories)
            }
        }
    }

    fun removeProduct(product: Product, categoryName: String) = viewModelScope.launch {
        val currentCategories = _state.value.shop?.shopCategories?.toMutableList() ?: return@launch
        val categoryIndex = currentCategories.indexOfFirst { it.categoryName == categoryName }
        
        if (categoryIndex != -1) {
            val category = currentCategories[categoryIndex]
            val updatedProducts = category.categoryProducts.toMutableList().apply {
                removeAll { it.productId == product.productId }
            }
            currentCategories[categoryIndex] = category.copy(categoryProducts = updatedProducts)
            updateShopField("shopCategories", currentCategories)
        }
    }

    private suspend fun updateShopField(field: String, value: Any) {
        try {
            val userId = auth.currentUser?.uid ?: return
            firestore.collection("shops").document(userId)
                .update(field, value)
                .await()
            
            // Update local state
            val updatedShop = _state.value.shop?.let { currentShop ->
                when (field) {
                    "shopName" -> currentShop.copy(shopName = value as String)
                    "shopAddress" -> currentShop.copy(shopAddress = value as String)
                    "shopImages" -> currentShop.copy(shopImages = value as List<String>)
                    "shopCategories" -> currentShop.copy(shopCategories = value as List<Category>)
                    else -> currentShop
                }
            }
            _state.value = _state.value.copy(shop = updatedShop)
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = e.message ?: "Failed to update shop")
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
}

