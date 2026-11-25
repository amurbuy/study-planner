package com.example.studyplanner.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsRepository {

    private val DARK_THEME = booleanPreferencesKey("dark_theme")

    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.applicationContext.dataStore
    }

    val darkTheme: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[DARK_THEME] ?: false
        }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME] = enabled
        }
    }
}