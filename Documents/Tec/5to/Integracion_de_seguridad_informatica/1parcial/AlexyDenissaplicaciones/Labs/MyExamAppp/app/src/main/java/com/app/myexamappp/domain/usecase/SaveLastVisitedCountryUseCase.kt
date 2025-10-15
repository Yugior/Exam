package com.app.myexamappp.domain.usecase

import com.app.myexamappp.domain.repository.CountriesRepository
import javax.inject.Inject

/**
 * Caso de uso para guardar el último país visitado
 * Se ejecuta cada vez que el usuario entra al detalle de un país
 */
class SaveLastVisitedCountryUseCase
    @Inject
    constructor(
        private val repository: CountriesRepository,
    ) {
        /**
         * Guarda el último país visitado en las preferencias
         * @param code Código del país
         * @param name Nombre del país
         */
        suspend operator fun invoke(
            code: String,
            name: String,
        ) {
            repository.saveLastVisitedCountry(code, name)
        }
    }
