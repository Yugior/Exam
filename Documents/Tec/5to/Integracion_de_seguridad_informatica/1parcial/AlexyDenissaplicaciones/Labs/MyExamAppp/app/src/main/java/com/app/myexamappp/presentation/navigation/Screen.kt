package com.app.myexamappp.presentation.navigation

/**
 * Definición de rutas de navegación
 * Sealed class para type-safety en la navegación
 */
sealed class Screen(
    val route: String,
) {
    /**
     * Pantalla principal con lista de países
     */
    object Home : Screen("home")

    /**
     * Pantalla de detalle de un país
     * Recibe el código del país como parámetro
     */
    object Detail : Screen("country/{countryCode}") {
        /**
         * Crea la ruta con el código del país
         * @param countryCode Código ISO del país (ej: MX, US, FR)
         */
        fun createRoute(countryCode: String) = "country/$countryCode"
    }
}
