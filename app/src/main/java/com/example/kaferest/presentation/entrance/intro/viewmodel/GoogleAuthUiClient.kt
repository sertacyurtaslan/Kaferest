package com.example.kaferest.presentation.entrance.intro.viewmodel

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.kaferest.R
import com.example.kaferest.domain.model.User
import com.example.kaferest.util.CurrentDate
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        return try {
            println("GoogleSignIn: Starting sign-in process")
            val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()

            oneTapClient.beginSignIn(signInRequest)
                .await()
                ?.pendingIntent
                ?.intentSender
        } catch (e: Exception) {
            println("GoogleSignIn: Error in signIn - ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun signInWithIntent(intent: Intent): GoogleSignInResult {
        val credential = try {
            oneTapClient.getSignInCredentialFromIntent(intent)
        } catch (e: Exception) {
            println("GoogleSignIn: Error getting credential - ${e.message}")
            return GoogleSignInResult(
                data = null,
                errorMessage = e.message,
                isSuccess = false
            )
        }

        val googleIdToken = credential.googleIdToken
        if (googleIdToken == null) {
            println("GoogleSignIn: Google ID token is null")
            return GoogleSignInResult(
                data = null,
                errorMessage = "Google ID token is null",
                isSuccess = false
            )
        }

        try {
            val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val user = authResult.user
            
            if (user == null) {
                println("GoogleSignIn: Firebase user is null")
                return GoogleSignInResult(
                    data = null,
                    errorMessage = "Firebase user is null",
                    isSuccess = false
                )
            }

            return GoogleSignInResult(
                data = User(
                    userId = user.uid,
                    userName = user.displayName ?: "",
                    userEmail = user.email ?: "",
                    userProfilePhoto = user.photoUrl?.toString() ?: "",
                    userCreationDate = CurrentDate().getFormattedDate()
                ),
                errorMessage = null,
                isNewUser = authResult.additionalUserInfo?.isNewUser ?: false,
                isSuccess = true
            )
        } catch (e: Exception) {
            println("GoogleSignIn: Error in signInWithIntent - ${e.message}")
            e.printStackTrace()
            return GoogleSignInResult(
                data = null,
                errorMessage = e.message,
                isSuccess = false
            )
        }
    }

    fun signOut() {
        try {
            auth.signOut()
            oneTapClient.signOut()
        } catch (e: Exception) {
            println("GoogleSignIn: Error in signOut - ${e.message}")
        }
    }

    fun getSignedInUser(): User? = auth.currentUser?.run {
        User(
            userId = uid,
            userName = displayName ?: "",
            userEmail = email ?: "",
            userProfilePhoto = photoUrl?.toString() ?: "",
            userCreationDate = "" // You might want to store this in Firestore
        )
    }
}
