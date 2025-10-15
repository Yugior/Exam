package com.app.myexamappp.domain.usecase

import com.app.myexamappp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obtener el último país visitado
 * Se usa al abrir la app para scrollear a la última posición
 */
class GetLastVisitedCountryUseCase
    @Inject
    constructor(
        private val repository: CountriesRepository,
    ) {
        /**
         * Obtiene el código del último país visitado
         * @return Flow con el código o null
         */
        fun getCode(): Flow<String?> = repository.getLastVisitedCountryCode()

        /**
         * Obtiene el nombre del último país visitado
         * @return Flow con el nombre o null
         */
        fun getName(): Flow<String?> = repository.getLastVisitedCountryName()
    }
