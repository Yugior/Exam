package com.app.myexamappp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO simplificado para la lista de países
 * Endpoint: https://restcountries.com/v3.1/all?fields=name,cca2,flags
 */
data class CountrySimpleDto(
    @SerializedName("name")
    val name: NameDto,
    @SerializedName("cca2")
    val code: String, // Código ISO de 2 letras (ej: MX, US, FR)
    @SerializedName("flags")
    val flags: FlagsDto,
) {
    data class NameDto(
        @SerializedName("common")
        val common: String, // Nombre común del país
        @SerializedName("official")
        val official: String?, // Nombre oficial
    )

    data class FlagsDto(
        @SerializedName("png")
        val png: String, // URL de la bandera en PNG
        @SerializedName("svg")
        val svg: String?, // URL de la bandera
    )
}
