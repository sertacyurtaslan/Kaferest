package com.example.kaferest.data.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLanguagePreference(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    fun getLanguagePreference(): String? {
        return prefs.getString(KEY_LANGUAGE, null)
    }

    fun getSpinGameLastPlayed(): Long {
        return prefs.getLong(KEY_SPIN_GAME_LAST_PLAYED, 0L)
    }

    fun saveSpinGameLastPlayed(timestamp: Long) {
        prefs.edit().putLong(KEY_SPIN_GAME_LAST_PLAYED, timestamp).apply()
    }

    fun getScratchGameLastPlayed(): Long {
        return prefs.getLong(KEY_SCRATCH_GAME_LAST_PLAYED, 0L)
    }

    fun saveScratchGameLastPlayed(timestamp: Long) {
        prefs.edit().putLong(KEY_SCRATCH_GAME_LAST_PLAYED, timestamp).apply()
    }

    companion object {
        private const val PREFS_NAME = "cafely_prefs"
        private const val KEY_LANGUAGE = "language_code"
        private const val KEY_SPIN_GAME_LAST_PLAYED = "spin_game_last_played"
        private const val KEY_SCRATCH_GAME_LAST_PLAYED = "scratch_game_last_played"
    }
} 