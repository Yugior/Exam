package com.app.myexamappp.presentation.utils

/**
 * Constantes de la aplicación
 * Incluye los textos para el Self-Explained Dialog (requerimiento del examen)
 */
object Constants {
    /**
     * Nombre del estudiante
     */
    const val STUDENT_NAME = "Horacio Villela Hernandez"

    /**
     * Explicación de la arquitectura
     * Pieza 1: Arquitectura elegida + interfaz de dominio real
     */
    const val ARCHITECTURE_EXPLANATION = """
        This app implements MVVM (Model-View-ViewModel) combined with Clean Architecture, separating concerns into three main layers:
        
        • Presentation Layer: Contains UI (Compose screens), ViewModels, and UI States
        • Domain Layer: Business logic with Use Cases and domain models (Country)
        • Data Layer: API clients, DTOs, Repository implementation, and local storage
        
        A key domain interface is CountriesRepository, which defines operations like:
        - getAllCountries(): List<Country>
        - getCountryByCode(code: String): Country
        - saveLastVisitedCountry(code: String, name: String)
        
        This separation allows us to change data sources without affecting business logic or UI.
    """

    /**
     * Estrategia de guardado de preferencias
     * Pieza 2: Cómo se guardan las preferencias del usuario
     */
    const val PREFERENCES_STRATEGY = """
        We use DataStore Preferences (the modern replacement for SharedPreferences) to persist user preferences.
        
        When a user visits a country detail screen, we save:
        • Country code (ISO 2-letter code like "MX", "US")
        • Country name (for display purposes)
        
        On app restart, we read these preferences and automatically scroll the list to the last visited country, providing continuity in the user experience.
        
        Implementation: PreferencesManager class wraps DataStore API with suspend functions for async read/write operations.
    """

    /**
     * Estrategia de búsqueda
     * Pieza 3: Cómo funciona el sistema de búsqueda
     */
    const val SEARCH_STRATEGY = """
        The search feature uses in-memory filtering on the cached country list for instant results:
        
        • Countries are loaded once from the API and cached in the ViewModel
        • Search query filters this cached list by matching:
          - Country name (case-insensitive)
          - Country code (ISO code)
        
        Benefits:
        • No additional API calls needed
        • Instant search results as user types
        • Works offline once data is loaded
        
        The filtering logic is reactive using Compose's remember with query and list dependencies, automatically recomputing when either changes.
    """
}
