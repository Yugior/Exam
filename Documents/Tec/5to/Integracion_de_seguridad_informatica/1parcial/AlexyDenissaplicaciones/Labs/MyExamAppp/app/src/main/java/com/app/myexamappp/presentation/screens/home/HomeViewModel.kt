package com.app.myexamappp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myexamappp.domain.common.Result
import com.app.myexamappp.domain.usecase.GetCountriesUseCase
import com.app.myexamappp.domain.usecase.GetLastVisitedCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla principal
 * Maneja la carga de países y la obtención del último país visitado
 */
@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getCountriesUseCase: GetCountriesUseCase,
        private val getLastVisitedCountryUseCase: GetLastVisitedCountryUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        init {
            loadCountries()
            loadLastVisitedCountry()
        }

        /**
         * Carga la lista de países desde la API
         */
        fun loadCountries() {
            viewModelScope.launch {
                getCountriesUseCase().collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> state.copy(isLoading = true)
                            is Result.Success ->
                                state.copy(
                                    countriesList = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message ?: "Unknown error occurred",
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }

        /**
         * Carga el código del último país visitado para scroll automático
         */
        private fun loadLastVisitedCountry() {
            viewModelScope.launch {
                getLastVisitedCountryUseCase.getCode().collect { code ->
                    _uiState.update { it.copy(lastVisitedCountryCode = code) }
                }
            }
        }
    }
