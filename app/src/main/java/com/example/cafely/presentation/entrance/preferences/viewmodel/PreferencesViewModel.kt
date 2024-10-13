package com.example.financecompose.presentation.entrance.preferences.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.cafely.domain.model.User
import com.example.cafely.domain.repository.CafelyRepository
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val repository: CafelyRepository,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(PreferencesScreenState())
    val uiState: State<PreferencesScreenState> = _uiState

    fun saveUserPreferences(userBalance: String, userCurrency: String) {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                val docRef = db.collection("users").document(userId)
                docRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            val updatedUser = user.copy(
                                userBalance = userBalance,
                                userCurrency = userCurrency
                            )
                            docRef.set(updatedUser)
                                .addOnSuccessListener {
                                    _uiState.value = _uiState.value.copy(successMessage = "Preferences updated successfully")
                                }
                                .addOnFailureListener { exception ->
                                    _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                                }
                        }
                    }
                }.addOnFailureListener { exception ->
                    _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                }
            } else {
                _uiState.value = _uiState.value.copy(errorMessage = "User is not logged in")
            }
        }
    }
}
