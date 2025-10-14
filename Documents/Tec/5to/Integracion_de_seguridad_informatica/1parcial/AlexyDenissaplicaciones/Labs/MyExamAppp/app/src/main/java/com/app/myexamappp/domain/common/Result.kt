package com.app.myexamappp.domain.common

sealed class Result<out T> {
    /**
     * Estado de carga
     */
    object Loading : Result<Nothing>()

    /**
     * Estado de éxito con datos
     */
    data class Success<T>(
        val data: T,
    ) : Result<T>()

    /**
     * Estado de error con excepción
     */
    data class Error(
        val exception: Throwable,
    ) : Result<Nothing>()
}
