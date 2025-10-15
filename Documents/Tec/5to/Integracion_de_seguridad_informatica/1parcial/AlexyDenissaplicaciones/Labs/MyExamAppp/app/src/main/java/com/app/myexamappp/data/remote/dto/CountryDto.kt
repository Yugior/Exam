package com.app.myexamappp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO completo para detalle de un país
 * Endpoint: https://restcountries.com/v3.1/name/{name}
 * o https://restcountries.com/v3.1/alpha/{code}
 */
data class CountryDto(
    @SerializedName("name")
    val name: NameDto,
    @SerializedName("cca2")
    val code: String,
    @SerializedName("capital")
    val capital: List<String>? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("subregion")
    val subregion: String? = null,
    @SerializedName("population")
    val population: Long? = null,
    @SerializedName("area")
    val area: Double? = null,
    @SerializedName("flags")
    val flags: FlagsDto,
    @SerializedName("languages")
    val languages: Map<String, String>? = null,
    @SerializedName("currencies")
    val currencies: Map<String, CurrencyDto>? = null,
    @SerializedName("timezones")
    val timezones: List<String>? = null,
    @SerializedName("borders")
    val borders: List<String>? = null,
    @SerializedName("maps")
    val maps: MapsDto? = null,
) {
    data class NameDto(
        @SerializedName("common")
        val common: String,
        @SerializedName("official")
        val official: String? = null,
        @SerializedName("nativeName")
        val nativeName: Map<String, NativeNameDto>? = null,
    ) {
        data class NativeNameDto(
            @SerializedName("common")
            val common: String,
            @SerializedName("official")
            val official: String,
        )
    }

    data class FlagsDto(
        @SerializedName("png")
        val png: String,
        @SerializedName("svg")
        val svg: String? = null,
        @SerializedName("alt")
        val alt: String? = null,
    )

    data class CurrencyDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("symbol")
        val symbol: String? = null,
    )

    data class MapsDto(
        @SerializedName("googleMaps")
        val googleMaps: String? = null,
        @SerializedName("openStreetMaps")
        val openStreetMaps: String? = null,
    )
}
