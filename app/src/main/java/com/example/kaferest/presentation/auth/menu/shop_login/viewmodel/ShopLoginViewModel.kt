package com.example.kaferest.presentation.auth.menu.shop_login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException

@HiltViewModel
class ShopLoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(ShopLoginState())
    val uiState: State<ShopLoginState> = _uiState

    private fun loginWithEmailPassword(email: String, password: String) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // First attempt Firebase Auth login
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            
            if (firebaseUser != null) {
                Log.d("AdminLogin", "Firebase Auth successful for email: $email")
                
                try {
                    // Query shops collection to find a shop with matching email
                    val shopQuery = firestore.collection("shops")
                        .whereEqualTo("shopMail", email)
                        .get()
                        .await()
                    
                    if (!shopQuery.isEmpty) {
                        val shopDoc = shopQuery.documents.first()
                        val shopEmail = shopDoc.getString("shopMail")
                        val shopName = shopDoc.getString("shopName") ?: ""
                        
                        Log.d("AdminLogin", "Found shop with email: $shopEmail, name: $shopName")
                        
                        if (shopEmail == email) {
                            if (shopName.isEmpty()) {
                                _uiState.value = _uiState.value.copy(
                                    isNewShop = true,
                                    isLoading = false,
                                    successMessage = "Welcome! Please set up your shop."
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    successMessage = "Login successful",
                                    isLoading = false
                                )
                            }
                        } else {
                            // This shouldn't happen due to the query, but just in case
                            auth.signOut()
                            _uiState.value = _uiState.value.copy(
                                matchError = "Email verification failed",
                                isLoading = false
                            )
                            Log.e("AdminLogin", "Email mismatch: $email vs $shopEmail")
                        }
                    } else {
                        // No shop found with this email
                        auth.signOut()
                        _uiState.value = _uiState.value.copy(
                            matchError = "This email is not registered as a shop owner",
                            isLoading = false
                        )
                        Log.d("AdminLogin", "No shop found for email: $email")
                    }
                } catch (e: FirebaseFirestoreException) {
                    auth.signOut()
                    when (e.code) {
                        FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                            Log.e("AdminLogin", "Firestore permission denied: ${e.message}")
                            _uiState.value = _uiState.value.copy(
                                matchError = "Access denied. Please contact support.",
                                isLoading = false
                            )
                        }
                        else -> {
                            Log.e("AdminLogin", "Firestore error: ${e.message}")
                            _uiState.value = _uiState.value.copy(
                                matchError = "Database error: ${e.message}",
                                isLoading = false
                            )
                        }
                    }
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    matchError = "Authentication failed",
                    isLoading = false
                )
                Log.e("AdminLogin", "Firebase user is null after successful auth")
            }
        } catch (e: Exception) {
            Log.e("AdminLogin", "Login error: ${e.message}", e)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = when {
                    e.message?.contains("password is invalid", ignoreCase = true) == true -> {
                        Log.d("AdminLogin", "Invalid password for email: $email")
                        "Invalid password"
                    }
                    e.message?.contains("no user record", ignoreCase = true) == true -> {
                        Log.d("AdminLogin", "No account found for email: $email")
                        "No account found with this email"
                    }
                    else -> {
                        Log.e("AdminLogin", "Unexpected error: ${e.message}")
                        "Authentication failed: ${e.message}"
                    }
                }
            )
        }
    }

    fun onEvent(event: ShopLoginEvent) {
        when (event) {
            is ShopLoginEvent.LoginOwner -> {
                loginWithEmailPassword(event.ownerMail, event.ownerPassword)
            }
        }
    }
}