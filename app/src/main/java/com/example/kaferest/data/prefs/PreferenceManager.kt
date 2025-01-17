package com.example.kaferest.data.prefs

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLanguagePreference(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    fun getLanguagePreference(): String? {
        return prefs.getString(KEY_LANGUAGE, null)
    }

    companion object {
        private const val PREFS_NAME = "cafely_prefs"
        private const val KEY_LANGUAGE = "language_code"
    }
} 