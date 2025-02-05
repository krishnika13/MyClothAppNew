package com.example.myclothappnew.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "profile_prefs")

class ProfileDataStore(private val context: Context) {
    private val PROFILE_IMAGE_KEY = stringPreferencesKey("profile_image_path")

    // This flow emits the saved profile image path or null if not present
    val profileImagePath: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PROFILE_IMAGE_KEY]
    }

    // Save the profile image path in DataStore
    suspend fun saveProfileImagePath(path: String) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_KEY] = path
        }
    }

    // Clear the profile image path from DataStore
    suspend fun clearProfileImagePath() {
        context.dataStore.edit { preferences ->
            preferences.remove(PROFILE_IMAGE_KEY)
        }
    }
}
