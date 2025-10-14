package com.app.myexamappp.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.myexamappp.presentation.common.components.ErrorView
import com.app.myexamappp.presentation.common.components.LoadingShimmer
import com.app.myexamappp.presentation.screens.detail.components.CountryCurrency
import com.app.myexamappp.presentation.screens.detail.components.CountryHeader
import com.app.myexamappp.presentation.screens.detail.components.CountryInfoSection
import com.app.myexamappp.presentation.screens.detail.components.CountryLanguages

/**
 * Pantalla de detalle del país
 * Muestra información completa con animaciones
 * Usa los componentes: CountryHeader, CountryInfoSection, CountryLanguages, CountryCurrency
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    countryCode: String,
    onBackClick: () -> Unit,
    viewModel: CountryDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Cargar detalle al entrar
    LaunchedEffect(countryCode) {
        viewModel.loadCountryDetail(countryCode)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = uiState.country?.name ?: "Country Details",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            when {
                uiState.isLoading -> {
                    // Loading state con shimmer
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        LoadingShimmer(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                        )
                        repeat(4) {
                            LoadingShimmer(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                            )
                        }
                    }
                }

                uiState.error != null -> {
                    ErrorView(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.loadCountryDetail(countryCode) },
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                uiState.country != null -> {
                    val country = uiState.country!!

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut(),
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            // Header: Bandera y nombre
                            CountryHeader(
                                flagUrl = country.flagUrl,
                                commonName = country.name,
                                officialName = country.officialName,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Información básica: Capital, Región, Población, Área
                            CountryInfoSection(country = country)

                            // Idiomas
                            CountryLanguages(languages = country.languages)

                            // Monedas, Zonas horarias y Países fronterizos
                            CountryCurrency(
                                currencies = country.currencies,
                                timezones = country.timezones,
                                borders = country.borders,
                            )
                        }
                    }
                }
            }
        }
    }
}
