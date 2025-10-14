package com.app.myexamappp.data.mapper

import com.app.myexamappp.data.remote.dto.CountryDto
import com.app.myexamappp.data.remote.dto.CountrySimpleDto
import com.app.myexamappp.domain.model.Country

/**
 * Convierte CountrySimpleDto (lista) a modelo de dominio
 */
fun CountrySimpleDto.toDomain(): Country =
    Country(
        code = code,
        name = name.common,
        officialName = name.official ?: name.common,
        flagUrl = flags.png,
        capital = null,
        region = null,
        subregion = null,
        population = null,
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
        code = code,
        name = name.common,
        officialName = name.official ?: name.common,
        flagUrl = flags.png,
        capital = capital?.firstOrNull(), // Tomamos la primera capital
        region = region,
        subregion = subregion,
        population = population,
        area = area,
        languages = languages?.values?.toList() ?: emptyList(),
        currencies =
            currencies?.map { (code, currency) ->
                "${currency.name}${currency.symbol?.let { " ($it)" } ?: ""}"
            } ?: emptyList(),
        timezones = timezones ?: emptyList(),
        borders = borders ?: emptyList(),
        mapUrl = maps?.googleMaps ?: maps?.openStreetMaps,
    )
