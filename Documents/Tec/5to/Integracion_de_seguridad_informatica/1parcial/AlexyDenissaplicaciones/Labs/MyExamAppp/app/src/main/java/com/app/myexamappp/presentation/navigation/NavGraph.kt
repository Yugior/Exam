package com.app.myexamappp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.myexamappp.presentation.screens.detail.CountryDetailScreen
import com.app.myexamappp.presentation.screens.home.HomeScreen

/**
 * Grafo de navegación de la aplicación
 * Define todas las rutas y transiciones entre pantallas
 */
@Composable
fun CountriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        // Pantalla principal (Home)
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCountryClick = { countryCode ->
                    navController.navigate(Screen.Detail.createRoute(countryCode))
                },
            )
        }

        // Pantalla de detalle (Country Detail)
        composable(
            route = Screen.Detail.route,
            arguments =
                listOf(
                    navArgument("countryCode") {
                        type = NavType.StringType
                    },
                ),
        ) { backStackEntry ->
            val countryCode = backStackEntry.arguments?.getString("countryCode") ?: ""
            CountryDetailScreen(
                countryCode = countryCode,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}
