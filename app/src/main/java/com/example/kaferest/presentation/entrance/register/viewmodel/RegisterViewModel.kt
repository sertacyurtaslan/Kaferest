package com.example.kaferest.presentation.entrance.register.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaferest.domain.model.User
import com.example.kaferest.domain.repository.KaferestRepository
import com.example.kaferest.util.CurrentDate
import com.example.kaferest.util.MailSender
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: KaferestRepository,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(RegisterScreenState())
    val uiState: State<RegisterScreenState> = _uiState

    private val _canResend = MutableStateFlow(true)
    val canResend: StateFlow<Boolean> = _canResend.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    init {
        db.collection("users").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                println("RegisterViewModel"+ "Firestore listener error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) =
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUserId = authResult.user?.uid
                    if (firebaseUserId == null) {
                        println("RegisterViewModel: Firebase User ID is null")
                        _uiState.value = _uiState.value.copy(errorMessage = "Firebase User ID is null")
                        return@addOnSuccessListener
                    }

                    println("RegisterViewModel: Firebase User ID is $firebaseUserId")

                    val userId = UUID.randomUUID().toString()
                    val user = User(
                        userId,
                        userName = name,
                        userEmail = email,
                        userCreationDate = CurrentDate().getFormattedDate()
                    )
                    saveUserToFirestore(user, userId)
                    _uiState.value = _uiState.value.copy(isRegistered = true)
                }
                .addOnFailureListener { exception ->
                    println("RegisterViewModel: Failed to create user - ${exception.localizedMessage}")
                    _uiState.value = _uiState.value.copy(emailError = exception.localizedMessage)
                }
        }

    private fun saveUserToFirestore(user: User, firebaseUserId: String) =
        viewModelScope.launch {
            val docRef = db.collection("users").document(firebaseUserId)
            println("RegisterViewModel: Firestore User ID: ${user.userId}")
            docRef.set(user)
                .addOnSuccessListener {
                    _uiState.value = _uiState.value.copy(successMessage = "User registered successfully", isRegistered = true)
                    println("RegisterViewModel: User saved to Firestore successfully")
                }
                .addOnFailureListener { exception ->
                    _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                    println("RegisterViewModel: Error saving user to Firestore - ${exception.localizedMessage}")
                }
        }

    private fun sendVerificationMail(userName: String, userMail: String, userPassword: String) =
        viewModelScope.launch {
        val newCode = generateVerificationCode()
        _uiState.value = _uiState.value.copy(verificationCode = newCode)
        println("Generating verification code: $newCode")

        MailSender.sendVerificationEmail(userMail, newCode)
            .onSuccess {
                _uiState.value = _uiState.value.copy(
                    successMessage = "Verification code sent successfully",
                    userName = userName,
                    userMail = userMail,
                    userPassword = userPassword
                )
            }

            .onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message ?: "Failed to send verification email"
                )
            }
    }

    private fun generateVerificationCode(): String {
        return Random.nextInt(100000, 999999).toString()
    }

    private fun verifyCodeAndRegister(inputCode: String): Boolean {
        println("Stored verification code: ${_uiState.value.verificationCode}")
        println("Input verification code: $inputCode")

        println("_uiState.value.userName, _uiState.value.userMail, _uiState.value.userPassword")

        return if (inputCode == _uiState.value.verificationCode && _uiState.value.verificationCode.isNotEmpty()) {
            println("Verification code is correct, proceeding with registration...")
            registerUser(_uiState.value.userName, _uiState.value.userMail, _uiState.value.userPassword)
            true
        } else {
            println("Verification failed. Input: $inputCode, Expected: ${_uiState.value.verificationCode}")
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid verification code"
            )
            false
        }
    }

    fun resendVerificationEmail() = viewModelScope.launch {
        if (_canResend.value) {
            _canResend.value = false
            _remainingSeconds.value = 60
            
            viewModelScope.launch {
                while (_remainingSeconds.value > 0) {
                    delay(1000)
                    _remainingSeconds.value--
                }
                _canResend.value = true
            }
            println("Mail to resend:"+_uiState.value.userMail)

            sendVerificationMail(
                _uiState.value.userName,
                _uiState.value.userMail,
                _uiState.value.userPassword)
        }
    }

    fun resetNavigation(){
        _uiState.value = _uiState.value.copy(isMailVerified = false)
    }

    private fun checkEmailExists(email: String) = viewModelScope.launch {
        try {
            // Set loading state to true when starting the check
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            auth.createUserWithEmailAndPassword(email, "temporary_password")
                .addOnCompleteListener { task ->
                    // Set loading state to false when check is complete
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    
                    if (task.isSuccessful) {
                        // Email is available, delete the temporary account
                        task.result?.user?.delete()?.addOnCompleteListener {
                            _uiState.value = _uiState.value.copy(
                                emailError = null,
                                isMailVerified = true
                            )
                            sendVerificationMail(
                                _uiState.value.userName,
                                email,
                                _uiState.value.userPassword
                            )
                        }
                    } else {
                        val exception = task.exception
                        when {
                            // Check specifically for email already in use
                            exception?.message?.contains("email address is already in use") == true -> {
                                _uiState.value = _uiState.value.copy(
                                    emailError = "This email is already registered",
                                    isMailVerified = false
                                )
                            }
                            // Handle other errors
                            else -> {
                                _uiState.value = _uiState.value.copy(
                                    emailError = exception?.message ?: "Error checking email",
                                    isMailVerified = false
                                )
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                emailError = e.message ?: "Error checking email",
                isMailVerified = false,
                isLoading = false
            )
        }
    }

    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.RegisterUser -> {
                registerUser(event.userName, event.userMail, event.userPassword)
            }
            is RegisterScreenEvent.SendVerificationMail -> {
                sendVerificationMail(event.userName, event.userMail, event.userPassword)
            }
            is RegisterScreenEvent.VerifyCodeAndRegister -> {
                verifyCodeAndRegister(event.inputCode)
            }
            is RegisterScreenEvent.CheckEmailExists -> {
                checkEmailExists(event.email)
            }
        }
    }

}

