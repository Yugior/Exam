package com.app.myexamappp.presentation.screens.detail

import com.app.myexamappp.domain.model.Country

/**
 * Estado UI para la pantalla de detalle del país
 */
data class CountryDetailUiState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
