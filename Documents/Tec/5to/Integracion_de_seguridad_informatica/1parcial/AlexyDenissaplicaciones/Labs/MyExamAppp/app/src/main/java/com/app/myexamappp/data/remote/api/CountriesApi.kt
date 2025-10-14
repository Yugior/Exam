package com.app.myexamappp.data.remote.api

import com.app.myexamappp.data.remote.dto.CountryDto
import com.app.myexamappp.data.remote.dto.CountrySimpleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API para consumir REST Countries
 * Base URL: https://restcountries.com/v3.1/
 * Documentación: https://restcountries.com/
 */
interface CountriesApi {
    /**
     * Obtiene lista básica de todos los países
     * Solo trae campos necesarios para optimizar la respuesta
     */
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,cca2,flags,population,region",
    ): List<CountrySimpleDto>

    /**
     * Obtiene detalle completo de un país por su nombre
     * @param name Nombre común del país (ej: "Mexico", "United States")
     */
    @GET("name/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String,
    ): List<CountryDto> // La API devuelve una lista (puede haber países con nombres similares)

    /**
     * Obtiene detalle completo de un país por su código ISO
     * @param code Código de 2 letras (ej: "MX", "US", "FR")
     */
    @GET("alpha/{code}")
    suspend fun getCountryByCode(
        @Path("code") code: String,
    ): CountryDto // Por código devuelve un solo objeto
}
