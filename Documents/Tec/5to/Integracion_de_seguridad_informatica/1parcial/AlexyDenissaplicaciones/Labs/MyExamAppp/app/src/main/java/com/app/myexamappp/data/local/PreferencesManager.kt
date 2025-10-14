package com.app.myexamappp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extensión para crear el DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "countries_preferences")

/**
 * Gestor de preferencias usando DataStore
 * Guarda el último país visitado para restaurar al reabrir la app
 */
@Singleton
class PreferencesManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val dataStore = context.dataStore

        companion object {
            private val LAST_VISITED_COUNTRY_CODE = stringPreferencesKey("last_visited_country_code")
            private val LAST_VISITED_COUNTRY_NAME = stringPreferencesKey("last_visited_country_name")
        }

        /**
         * Guarda el código y nombre del último país visitado
         */
        suspend fun saveLastVisitedCountry(
            code: String,
            name: String,
        ) {
            dataStore.edit { preferences ->
                preferences[LAST_VISITED_COUNTRY_CODE] = code
                preferences[LAST_VISITED_COUNTRY_NAME] = name
            }
        }

        /**
         * Obtiene el código del último país visitado
         * @return Flow con el código del país o null si no hay ninguno guardado
         */
        fun getLastVisitedCountryCode(): Flow<String?> =
            dataStore.data.map { preferences ->
                preferences[LAST_VISITED_COUNTRY_CODE]
            }

        /**
         * Obtiene el nombre del último país visitado
         * @return Flow con el nombre del país o null si no hay ninguno guardado
         */
        fun getLastVisitedCountryName(): Flow<String?> =
            dataStore.data.map { preferences ->
                preferences[LAST_VISITED_COUNTRY_NAME]
            }

        /**
         * Limpia las preferencias guardadas
         */
        suspend fun clearPreferences() {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
