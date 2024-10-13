package com.example.cafely.presentation.entrance.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafely.domain.repository.CafelyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val cafelyRepository: CafelyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignInState())
    val state: StateFlow<GoogleSignInState> = _state.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    fun onSignInResult(signInResult: GoogleSignInResult) =
        viewModelScope.launch {
            if (signInResult.isSuccess) {
                val userId = signInResult.data?.userId
                if (userId != null) {
                    val user = cafelyRepository.getUser(userId)
                    if (user != null) {
                        _state.value = GoogleSignInState(isSignInSuccessful = true, user = user)
                    } else {
                        val newUser = cafelyRepository.createUser(signInResult.data)
                        _state.value = GoogleSignInState(isSignInSuccessful = true, user = newUser, isNewUser = true)
                    }
                } else {
                    _state.value = GoogleSignInState(signInError = "User ID is null")
                }
            } else {
                _state.value = GoogleSignInState(signInError = signInResult.errorMessage)
            }
        }
    }
