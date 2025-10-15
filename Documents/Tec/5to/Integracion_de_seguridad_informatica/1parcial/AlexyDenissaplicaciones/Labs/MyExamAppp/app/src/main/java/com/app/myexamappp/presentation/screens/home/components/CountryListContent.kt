package com.app.myexamappp.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.myexamappp.domain.model.Country
import com.app.myexamappp.presentation.common.components.ErrorView
import com.app.myexamappp.presentation.common.components.LoadingShimmer

@Suppress("ktlint:standard:function-naming")
/**
 * Contenido de la lista de países
 * Incluye pull-to-refresh, shimmer loading y scroll automático al último país visitado
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListContent(
    countriesList: List<Country>,
    isLoading: Boolean,
    error: String?,
    onCountryClick: (String) -> Unit,
    onRetry: () -> Unit,
    lastVisitedCountryCode: String?,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = onRetry,
        )

    // Scroll automático al último país visitado
    LaunchedEffect(lastVisitedCountryCode, countriesList) {
        if (lastVisitedCountryCode != null && countriesList.isNotEmpty()) {
            val index = countriesList.indexOfFirst { it.code == lastVisitedCountryCode }
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
    ) {
        when {
            isLoading && countriesList.isEmpty() -> {
                // Shimmer loading para primera carga
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(10) {
                        LoadingShimmer(
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }

            error != null && countriesList.isEmpty() -> {
                ErrorView(
                    message = error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            else -> {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = countriesList,
                        key = { it.code },
                    ) { country ->
                        CountryCard(
                            country = country,
                            onClick = { onCountryClick(country.code) },
                        )
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            scale = true,
        )
    }
}
