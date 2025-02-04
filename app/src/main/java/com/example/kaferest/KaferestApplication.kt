package com.example.kaferest

import android.app.Application
import android.util.Log
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KaferestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        try {
            // Initialize Firebase first
            FirebaseApp.initializeApp(this)?.let { app ->
                Log.d("Firebase", "Firebase initialized successfully")

                // Initialize App Check with Play Integrity
                val firebaseAppCheck = FirebaseAppCheck.getInstance()
                
                if (BuildConfig.DEBUG) {
                    // For debug builds, use debug provider
                    firebaseAppCheck.installAppCheckProviderFactory(
                        DebugAppCheckProviderFactory.getInstance()
                    )
                    // Set up Firebase Auth emulator
                    FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099)
                    // Disable app verification for testing
                    FirebaseAuth.getInstance().firebaseAuthSettings
                        .setAppVerificationDisabledForTesting(true)
                    Log.d("AppCheck", "Debug mode: Using debug provider")
                } else {
                    // For release builds, use Play Integrity provider
                    firebaseAppCheck.installAppCheckProviderFactory(
                        PlayIntegrityAppCheckProviderFactory.getInstance()
                    )
                    Log.d("AppCheck", "Release mode: Using Play Integrity provider")
                }

                // Enable token auto-refresh
                firebaseAppCheck.setTokenAutoRefreshEnabled(true)

            } ?: throw IllegalStateException("FirebaseApp initialization failed")

        } catch (e: Exception) {
            Log.e("Firebase", "Firebase initialization error: ${e.message}")
            e.printStackTrace()
        }
    }
}