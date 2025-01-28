package com.example.kaferest.presentation.entrance.admin_login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.presentation.entrance.admin_login.viewmodel.AdminLoginEvent
import com.example.kaferest.presentation.entrance.admin_login.viewmodel.AdminLoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(AdminLoginState())
    val uiState: State<AdminLoginState> = _uiState

    private fun loginOwner(email: String, password: String) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Query Firestore to check owner credentials
            val ownersRef = firestore.collection("owners")
            val querySnapshot = ownersRef.get().await()
            
            var isValidOwner = false
            for (document in querySnapshot.documents) {
                val ownerMail = document.getString("ownerMail")
                val ownerPassword = document.getString("ownerPassword")
                
                if (ownerMail == email && ownerPassword == password) {
                    isValidOwner = true
                    println("dedengil geldi mi ")
                    break
                }
            }
            
            if (isValidOwner) {
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