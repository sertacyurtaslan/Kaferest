package com.example.cafely.presentation.entrance.intro.viewmodel

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.cafely.R
import com.example.cafely.domain.model.User
import com.example.cafely.util.CurrentDate
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
            result?.pendingIntent?.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    suspend fun signInWithIntent(intent: Intent): GoogleSignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val authResult = auth.signInWithCredential(googleCredentials).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            val user = authResult.user
            GoogleSignInResult(
                data = user?.run {
                    User(
                        userId = uid,
                        userName = displayName,
                        userEmail = email,
                        userProfilePhoto = photoUrl.toString(), // Fetch profile photo URL
                        userCreationDate = CurrentDate().getFormattedDate()
                    )
                },
                errorMessage = null,
                isNewUser = isNewUser,
                isSuccess = true
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            GoogleSignInResult(
                data = null,
                errorMessage = e.message,
                isNewUser = false,
                isSuccess = false
            )
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
