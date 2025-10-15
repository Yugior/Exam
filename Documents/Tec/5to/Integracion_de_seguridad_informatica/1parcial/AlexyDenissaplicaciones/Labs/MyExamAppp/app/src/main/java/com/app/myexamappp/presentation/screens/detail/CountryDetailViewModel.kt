package com.app.myexamappp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myexamappp.domain.common.Result
import com.app.myexamappp.domain.usecase.GetCountryDetailUseCase
import com.app.myexamappp.domain.usecase.SaveLastVisitedCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de detalle del país
 * Carga la información completa y guarda el país como último visitado
 */
@HiltViewModel
class CountryDetailViewModel
    @Inject
    constructor(
        private val getCountryDetailUseCase: GetCountryDetailUseCase,
        private val saveLastVisitedCountryUseCase: SaveLastVisitedCountryUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CountryDetailUiState())
        val uiState: StateFlow<CountryDetailUiState> = _uiState.asStateFlow()

        /**
         * Carga el detalle completo de un país por su código
         * También guarda el país como último visitado
         */
        fun loadCountryDetail(code: String) {
            viewModelScope.launch {
                getCountryDetailUseCase(code).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> state.copy(isLoading = true)
                            is Result.Success -> {
                                // Guardar como último visitado
                                saveLastVisitedCountryUseCase(
                                    code = result.data.code,
                                    name = result.data.name,
                                )

                                state.copy(
                                    country = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            }
                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message ?: "Failed to load country details",
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }
    }
