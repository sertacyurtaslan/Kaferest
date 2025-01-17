package com.example.kaferest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.kaferest.data.prefs.PreferenceManager
import com.example.kaferest.presentation.navigation.ScreensNavigation
import com.example.kaferest.ui.theme.CafelyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferenceManager: PreferenceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyLanguage()
        
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            CafelyTheme {
                ScreensNavigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        applyLanguage()
    }

    private fun applyLanguage() {
        val languageCode = preferenceManager.getLanguagePreference() ?: return
        changeLocaleAndRecreate(languageCode, applyOnly = true)
    }

    fun changeLocaleAndRecreate(languageCode: String, applyOnly: Boolean = false) {
        preferenceManager.saveLanguagePreference(languageCode)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        if (!applyOnly) {
            recreate()
        }
    }
}
