package com.example.kaferest.presentation.entrance.admin_login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userManager: UserManager,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(AdminLoginState())
    val uiState: State<AdminLoginState> = _uiState

    private fun loginWithEmailPassword(email: String, password: String) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // First attempt Firebase Auth login
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            
            if (firebaseUser != null) {
                // Check if this email matches the shop's email in Firestore
                val shopDoc = firestore.collection("shops")
                    .document(firebaseUser.uid)
                    .get()
                    .await()
                
                val shopEmail = shopDoc.getString("shopMail")
                val shopName = shopDoc.getString("shopName") ?: ""
                
                if (shopEmail == email) {
                    if (shopName.isEmpty()) {
                        _uiState.value = _uiState.value.copy(
                            isNewShop = true,
                            isLoading = false
                        )
                    }
                    // Save admin data using UserManager
                    _uiState.value = _uiState.value.copy(
                        successMessage = "Login successful",
                        isLoading = false
                    )
                } else {
                    // Email doesn't match, logout and show error
                    auth.signOut()
                    _uiState.value = _uiState.value.copy(
                        matchError = "This email is not authorized for shop management",
                        isLoading = false
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    matchError = "Invalid credentials",
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = when {
                    e.message?.contains("password is invalid", ignoreCase = true) == true -> 
                        "Invalid password"
                    e.message?.contains("no user record", ignoreCase = true) == true -> 
                        "No account found with this email"
                    else -> e.message ?: "Authentication failed"
                },
                matchError = "Check your email and password"
            )
        }
    }

    fun onEvent(event: AdminLoginEvent) {
        when (event) {
            is AdminLoginEvent.LoginOwner -> {
                loginWithEmailPassword(event.ownerMail, event.ownerPassword)
            }
        }
    }

}