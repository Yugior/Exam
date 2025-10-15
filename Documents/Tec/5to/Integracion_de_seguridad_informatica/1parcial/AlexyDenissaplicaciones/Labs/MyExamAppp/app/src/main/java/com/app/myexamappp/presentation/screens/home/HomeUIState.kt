package com.app.myexamappp.presentation.screens.home

import com.app.myexamappp.domain.model.Country

/**
 * Estado UI para la pantalla principal
 * Representa todos los estados posibles de la UI
 */
data class HomeUiState(
    val countriesList: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastVisitedCountryCode: String? = null, // Para scroll automático
)
