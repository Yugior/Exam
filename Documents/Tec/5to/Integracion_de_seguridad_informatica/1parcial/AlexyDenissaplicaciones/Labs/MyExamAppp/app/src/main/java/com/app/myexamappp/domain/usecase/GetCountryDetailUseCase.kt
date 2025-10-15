package com.app.myexamappp.domain.usecase

import com.app.myexamappp.domain.common.Result
import com.app.myexamappp.domain.model.Country
import com.app.myexamappp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Caso de uso para obtener el detalle completo de un país
 * Puede buscar por código ISO o por nombre
 */
class GetCountryDetailUseCase
    @Inject
    constructor(
        private val repository: CountriesRepository,
    ) {
        /**
         * Obtiene un país por su código ISO
         * @param code Código de 2 letras (ej: MX, US, FR)
         * @return Flow que emite estados de carga, éxito o error
         */
        operator fun invoke(code: String): Flow<Result<Country>> =
            flow {
                try {
                    emit(Result.Loading)

                    val country = repository.getCountryByCode(code)

                    emit(Result.Success(country))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }

        /**
         * Obtiene un país por su nombre
         * @param name Nombre común del país
         * @return Flow que emite estados de carga, éxito o error
         */
        fun byName(name: String): Flow<Result<Country>> =
            flow {
                try {
                    emit(Result.Loading)

                    val country = repository.getCountryByName(name)

                    emit(Result.Success(country))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
