package com.app.myexamappp.data.mapper

import com.app.myexamappp.data.remote.dto.CountryDto
import com.app.myexamappp.data.remote.dto.CountrySimpleDto
import com.app.myexamappp.domain.model.Country

/**
 * Convierte CountrySimpleDto (lista) a modelo de dominio
 */
fun CountrySimpleDto.toDomain(): Country =
    Country(
        code = this.code,
        name = this.name.common,
        officialName = this.name.official ?: this.name.common,
        flagUrl = this.flags.png,
        capital = null,
        region = this.region,
        subregion = null,
        population = this.population,
        area = null,
        languages = emptyList(),
        currencies = emptyList(),
        timezones = emptyList(),
        borders = emptyList(),
        mapUrl = null,
    )

/**
 * Convierte CountryDto (detalle completo) a modelo de dominio
 */
fun CountryDto.toDomain(): Country =
    Country(
        code = this.code,
        name = this.name.common,
        officialName = this.name.official ?: this.name.common,
        flagUrl = this.flags.png,
        capital = this.capital?.firstOrNull(),
        region = this.region,
        subregion = this.subregion,
        population = this.population,
        area = this.area,
        languages = this.languages?.values?.toList() ?: emptyList(),
        currencies =
            this.currencies?.map { (currencyCode, currency) ->
                "${currency.name}${currency.symbol?.let { " ($it)" } ?: ""}"
            } ?: emptyList(),
        timezones = this.timezones ?: emptyList(),
        borders = this.borders ?: emptyList(),
        mapUrl = this.maps?.googleMaps ?: this.maps?.openStreetMaps,
    )
