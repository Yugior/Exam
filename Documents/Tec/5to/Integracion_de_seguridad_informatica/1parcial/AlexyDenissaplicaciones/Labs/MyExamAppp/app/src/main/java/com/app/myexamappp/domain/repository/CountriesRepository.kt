package com.app.myexamappp.domain.repository

import com.app.myexamappp.domain.model.Country
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz del repositorio de países
 * Define el contrato que debe cumplir la implementación en la capa de datos
 *
 * Esta interfaz es clave en Clean Architecture:
 * - Está en la capa de dominio (núcleo de la aplicación)
 * - No conoce detalles de implementación (API, base de datos, etc.)
 * - Permite cambiar la implementación sin afectar los casos de uso
 */
interface CountriesRepository {
    /**
     * Obtiene la lista de todos los países
     * @return Lista de países del dominio
     */
    suspend fun getAllCountries(): List<Country>

    /**
     * Obtiene el detalle completo de un país por su código ISO
     * @param code Código de 2 letras del país (ej: MX, US, FR)
     * @return País con información completa
     */
    suspend fun getCountryByCode(code: String): Country

    /**
     * Obtiene el detalle completo de un país por su nombre
     * @param name Nombre común del país
     * @return País con información completa
     */
    suspend fun getCountryByName(name: String): Country

    /**
     * Guarda el último país visitado por el usuario
     * Se usa para restaurar la posición al reabrir la app
     * @param code Código del país
     * @param name Nombre del país
     */
    suspend fun saveLastVisitedCountry(
        code: String,
        name: String,
    )

    /**
     * Obtiene el código del último país visitado
     * @return Flow con el código o null si no hay ninguno guardado
     */
    fun getLastVisitedCountryCode(): Flow<String?>

    /**
     * Obtiene el nombre del último país visitado
     * @return Flow con el nombre o null si no hay ninguno guardado
     */
    fun getLastVisitedCountryName(): Flow<String?>

    /**
     * Limpia el caché en memoria
     * Útil para forzar una recarga de datos
     */
    suspend fun clearCache()
}
