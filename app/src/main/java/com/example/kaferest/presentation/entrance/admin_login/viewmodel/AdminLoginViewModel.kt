package com.example.kaferest.presentation.entrance.admin_login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.manager.UserManager
import com.example.kaferest.domain.model.User
import com.example.kaferest.util.CurrentDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiState = mutableStateOf(AdminLoginState())
    val uiState: State<AdminLoginState> = _uiState

    private fun loginOwner(email: String, password: String) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Query Firestore to check owner credentials
            val ownersRef = firestore.collection("shops")
            val querySnapshot = ownersRef.get().await()
            
            var isValidOwner = false
            var shopDocument: Map<String, Any>? = null

            for (document in querySnapshot.documents) {
                val shopId = document.id
                val shopName = document.getString("shopName")
                val shopMail = document.getString("shopMail")
                val shopPassword = document.getString("shopPassword")

                if (shopMail == email && shopPassword == password) {
                    // Create admin user data
                    val adminData = User(
                        userId = shopId,
                        userName = shopName ?: "",
                        userEmail = shopMail ?: "",
                        userCreationDate = CurrentDate().getFormattedDate(),
                    )

                    // Save admin data using UserManager
                    userManager.saveAdmin(adminData)
                    println("admin data saved")
                    isValidOwner = true
                    shopDocument = document.data
                    break
                }
            }

            if (isValidOwner && shopDocument != null) {
                _uiState.value = _uiState.value.copy(
                    successMessage = "Login successful",
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    matchError = "Invalid owner credentials"
                )
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = e.localizedMessage,
                matchError = "Check your email and password"
            )
        }
    }

    fun onEvent(event: AdminLoginEvent) {
        when (event) {
            is AdminLoginEvent.LoginOwner -> {
                loginOwner(event.ownerMail, event.ownerPassword)
            }
        }
    }
}