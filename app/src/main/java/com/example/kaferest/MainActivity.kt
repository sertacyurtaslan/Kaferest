package com.example.kaferest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kaferest.data.preferences.PreferenceManager
import com.example.kaferest.data.preferences.ThemePreferences
import com.example.kaferest.presentation.navigation.KaferestNavigation
import com.example.kaferest.ui.theme.KaferestTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferenceManager: PreferenceManager
    
    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyLanguage()
        
        setContent {
            val isDarkMode by themePreferences.isDarkMode.collectAsState(initial = false)

            KaferestTheme(darkTheme = isDarkMode) {
                KaferestNavigation()
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
        val config = Configuration()
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        if (!applyOnly) {
            recreate()
        }
    }
}
