package com.app.myexamappp.data.repository

import com.app.myexamappp.data.local.PreferencesManager
import com.app.myexamappp.data.mapper.toDomain
import com.app.myexamappp.data.remote.api.CountriesApi
import com.app.myexamappp.domain.model.Country
import com.app.myexamappp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación del repositorio de países
 * Combina la API REST con el almacenamiento local de preferencias
 */
@Singleton
class CountriesRepositoryImpl
    @Inject
    constructor(
        private val api: CountriesApi,
        private val preferencesManager: PreferencesManager,
    ) : CountriesRepository {
        // Caché en memoria para optimizar búsquedas
        private var cachedCountries: List<Country>? = null

        /**
         * Obtiene la lista de todos los países
         * Usa caché en memoria si está disponible
         */
        override suspend fun getAllCountries(): List<Country> {
            // Si ya tenemos caché, lo retornamos
            cachedCountries?.let { return it }

            // Si no, hacemos la llamada a la API
            val countries = api.getAllCountries().map { it.toDomain() }

            // Guardamos en caché
            cachedCountries = countries

            return countries
        }

        /**
         * Obtiene el detalle completo de un país por su código
         */
        override suspend fun getCountryByCode(code: String): Country = api.getCountryByCode(code).toDomain()

        /**
         * Obtiene el detalle completo de un país por su nombre
         * Si hay múltiples resultados, devuelve el primero
         */
        override suspend fun getCountryByName(name: String): Country {
            val results = api.getCountryByName(name)
            if (results.isEmpty()) {
                throw NoSuchElementException("Country not found: $name")
            }
            return results.first().toDomain()
        }

        /**
         * Guarda el último país visitado en preferencias
         */
        override suspend fun saveLastVisitedCountry(
            code: String,
            name: String,
        ) {
            preferencesManager.saveLastVisitedCountry(code, name)
        }

        /**
         * Obtiene el código del último país visitado
         */
        override fun getLastVisitedCountryCode(): Flow<String?> = preferencesManager.getLastVisitedCountryCode()

        /**
         * Obtiene el nombre del último país visitado
         */
        override fun getLastVisitedCountryName(): Flow<String?> = preferencesManager.getLastVisitedCountryName()

        /**
         * Limpia el caché en memoria
         * Útil para forzar una recarga desde la API
         */
        override suspend fun clearCache() {
            cachedCountries = null
        }
    }
