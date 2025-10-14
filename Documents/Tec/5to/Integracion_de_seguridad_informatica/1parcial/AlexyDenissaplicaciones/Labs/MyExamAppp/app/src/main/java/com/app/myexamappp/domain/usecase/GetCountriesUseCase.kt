package com.app.myexamappp.domain.usecase

import com.app.myexamappp.domain.common.Result
import com.app.myexamappp.domain.model.Country
import com.app.myexamappp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de países
 * Encapsula la lógica de negocio para cargar todos los países
 */
class GetCountriesUseCase
    @Inject
    constructor(
        private val repository: CountriesRepository,
    ) {
        /**
         * Ejecuta el caso de uso
         * @return Flow que emite estados de carga, éxito o error
         */
        operator fun invoke(): Flow<Result<List<Country>>> =
            flow {
                try {
                    // Emitir estado de carga
                    emit(Result.Loading)

                    // Obtener datos del repositorio
                    val countries = repository.getAllCountries()

                    // Emitir estado de éxito con los datos
                    emit(Result.Success(countries))
                } catch (e: Exception) {
                    // Emitir estado de error
                    emit(Result.Error(e))
                }
            }
    }
