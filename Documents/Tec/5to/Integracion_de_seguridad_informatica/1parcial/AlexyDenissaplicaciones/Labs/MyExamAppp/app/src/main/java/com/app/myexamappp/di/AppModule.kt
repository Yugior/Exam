package com.app.myexamappp.di

import com.app.myexamappp.data.remote.api.CountriesApi
import com.app.myexamappp.data.repository.CountriesRepositoryImpl
import com.app.myexamappp.domain.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de Hilt para inyección de dependencias
 * Proporciona instancias singleton de:
 * - Retrofit (cliente HTTP)
 * - CountriesApi (interfaz de la API)
 * - CountriesRepository (implementación del repositorio)
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provee la instancia de Retrofit configurada para REST Countries API
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Provee la instancia de CountriesApi
     * Retrofit crea la implementación automáticamente
     */
    @Provides
    @Singleton
    fun provideCountriesApi(retrofit: Retrofit): CountriesApi = retrofit.create(CountriesApi::class.java)

    /**
     * Provee la implementación del repositorio
     * Hilt inyecta automáticamente CountriesApi y PreferencesManager
     */
    @Provides
    @Singleton
    fun provideCountriesRepository(
        api: CountriesApi,
        preferencesManager: com.app.myexamappp.data.local.PreferencesManager,
    ): CountriesRepository = CountriesRepositoryImpl(api, preferencesManager)
}
