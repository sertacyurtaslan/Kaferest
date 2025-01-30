package com.example.kaferest.domain.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kaferest.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserManager @Inject constructor(
    private val context: Context
) {
    private val gson = Gson()

    companion object {
        private val USER_KEY = stringPreferencesKey("signed_in_user")
        private val ADMIN_KEY = stringPreferencesKey("signed_in_admin")
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = gson.toJson(user)
            // When saving a regular user, clear any admin data
            preferences.remove(ADMIN_KEY)
        }
    }

    suspend fun saveAdmin(admin: User) {
        context.dataStore.edit { preferences ->
            preferences[ADMIN_KEY] = gson.toJson(admin)
            // When saving an admin, clear any regular user data
            preferences.remove(USER_KEY)
        }
    }

    val user: Flow<User?> = context.dataStore.data.map { preferences ->
        preferences[USER_KEY]?.let { userJson ->
            try {
                gson.fromJson(userJson, User::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    val admin: Flow<User?> = context.dataStore.data.map { preferences ->
        preferences[ADMIN_KEY]?.let { adminJson ->
            try {
                gson.fromJson(adminJson, User::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }

    suspend fun clearAdmin() {
        context.dataStore.edit { preferences ->
            preferences.remove(ADMIN_KEY)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
            preferences.remove(ADMIN_KEY)
        }
    }
} 