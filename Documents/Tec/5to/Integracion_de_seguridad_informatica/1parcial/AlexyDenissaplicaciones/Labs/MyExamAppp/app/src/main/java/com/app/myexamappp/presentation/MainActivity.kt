package com.app.myexamappp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.app.myexamappp.presentation.navigation.CountriesNavGraph
import com.app.myexamappp.presentation.ui.theme.CountriesAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity de la aplicación Countries Explorer
 *
 * Autor: Horacio Villela Hernandez
 *
 * Anotada con @AndroidEntryPoint para que Hilt pueda inyectar dependencias
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountriesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CountriesNavGraph()
                }
            }
        }
    }
}
