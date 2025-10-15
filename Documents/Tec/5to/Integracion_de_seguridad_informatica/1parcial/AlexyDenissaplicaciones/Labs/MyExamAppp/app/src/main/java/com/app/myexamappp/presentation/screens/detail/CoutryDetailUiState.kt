package com.app.myexamappp.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Public
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.myexamappp.presentation.common.components.ErrorView
import com.app.myexamappp.presentation.common.components.LoadingShimmer
import com.app.myexamappp.presentation.screens.detail.components.ChipGroup
import com.app.myexamappp.presentation.screens.detail.components.CountryInfoRow
import com.app.myexamappp.presentation.screens.detail.components.InfoSection

/**
 * Pantalla de detalle del país
 * Muestra información completa con animaciones
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
                            // Bandera y nombre
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                AsyncImage(
                                    model = country.flagUrl,
                                    contentDescription = "${country.name} flag",
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .padding(horizontal = 32.dp),
                                    contentScale = ContentScale.Fit,
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = country.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                )

                                Text(
                                    text = country.officialName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }

                            // Información básica
                            InfoSection(title = "Basic Information") {
                                country.capital?.let {
                                    CountryInfoRow(
                                        icon = Icons.Default.LocationOn,
                                        label = "Capital",
                                        value = it,
                                    )
                                }

                                country.region?.let {
                                    CountryInfoRow(
                                        icon = Icons.Default.Public,
                                        label = "Region",
                                        value = "${it}${country.subregion?.let { sub -> " - $sub" } ?: ""}",
                                    )
                                }

                                country.population?.let {
                                    CountryInfoRow(
                                        icon = Icons.Default.People,
                                        label = "Population",
                                        value = country.getFormattedPopulation(),
                                    )
                                }

                                country.area?.let {
                                    CountryInfoRow(
                                        icon = Icons.Default.Map,
                                        label = "Area",
                                        value = country.getFormattedArea(),
                                    )
                                }
                            }

                            // Idiomas
                            if (country.languages.isNotEmpty()) {
                                InfoSection(title = "Languages") {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Language,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        ChipGroup(items = country.languages)
                                    }
                                }
                            }

                            // Monedas
                            if (country.currencies.isNotEmpty()) {
                                InfoSection(title = "Currencies") {
                                    ChipGroup(items = country.currencies)
                                }
                            }

                            // Zonas horarias
                            if (country.timezones.isNotEmpty()) {
                                InfoSection(title = "Timezones") {
                                    ChipGroup(items = country.timezones)
                                }
                            }

                            // Países fronterizos
                            if (country.borders.isNotEmpty()) {
                                InfoSection(title = "Border Countries") {
                                    ChipGroup(items = country.borders)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

xd, escribo esto porque no me dejaba mandar commit sin modificar algo
