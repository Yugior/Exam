package com.app.myexamappp.domain.model

data class Country(
    val code: String, // Código ISO de 2 letras (ej: MX, US, FR)
    val name: String, // Nombre común del país
    val officialName: String, // Nombre oficial
    val flagUrl: String, // URL de la bandera
    val capital: String?, // Capital (puede ser null)
    val region: String?, // Región (ej: Americas, Europe)
    val subregion: String?, // Subregión (ej: South America)
    val population: Long?, // Población
    val area: Double?, // Área en km²
    val languages: List<String>, // Lista de idiomas oficiales
    val currencies: List<String>, // Lista de monedas
    val timezones: List<String>, // Zonas horarias
    val borders: List<String>, // Códigos de países fronterizos
    val mapUrl: String?,
) {
    /**
     * Población formateada con separadores de miles
     * Ejemplo: 126,014,024
     */
    fun getFormattedPopulation(): String =
        population?.let {
            String.format("%,d", it)
        } ?: "N/A"

    /**
     * Área formateada con separadores de miles y unidad
     * Ejemplo: 1,964,375 km²
     */
    fun getFormattedArea(): String =
        area?.let {
            String.format("%,.0f km²", it)
        } ?: "N/A"

    /**
     * Idiomas en formato de texto legible
     * Ejemplo: "Spanish, English"
     */
    fun getLanguagesText(): String = languages.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "N/A"

    /**
     * Monedas en formato de texto legible
     * Ejemplo: "Mexican peso ($)"
     */
    fun getCurrenciesText(): String = currencies.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "N/A"

    /**
     * Verifica si el país tiene toda la información completa
     */
    fun hasCompleteInfo(): Boolean =
        capital != null &&
            region != null &&
            population != null &&
            languages.isNotEmpty()

    companion object {
        /**
         * Datos mock para desarrollo y preview
         */
        fun getMockData(): List<Country> =
            listOf(
                Country(
                    code = "MX",
                    name = "Mexico",
                    officialName = "United Mexican States",
                    flagUrl = "https://flagcdn.com/w320/mx.png",
                    capital = "Mexico City",
                    region = "Americas",
                    subregion = "North America",
                    population = 128932753,
                    area = 1964375.0,
                    languages = listOf("Spanish"),
                    currencies = listOf("Mexican peso ($)"),
                    timezones = listOf("UTC-08:00", "UTC-07:00", "UTC-06:00"),
                    borders = listOf("BLZ", "GTM", "USA"),
                    mapUrl = "https://goo.gl/maps/s5S7V",
                ),
                Country(
                    code = "US",
                    name = "United States",
                    officialName = "United States of America",
                    flagUrl = "https://flagcdn.com/w320/us.png",
                    capital = "Washington, D.C.",
                    region = "Americas",
                    subregion = "North America",
                    population = 329484123,
                    area = 9372610.0,
                    languages = listOf("English"),
                    currencies = listOf("United States dollar ($)"),
                    timezones =
                        listOf(
                            "UTC-12:00",
                            "UTC-11:00",
                            "UTC-10:00",
                            "UTC-09:00",
                            "UTC-08:00",
                            "UTC-07:00",
                            "UTC-06:00",
                            "UTC-05:00",
                            "UTC-04:00",
                        ),
                    borders = listOf("CAN", "MEX"),
                    mapUrl = "https://goo.gl/maps/e8M246zY4BSjkjAv6",
                ),
                Country(
                    code = "CA",
                    name = "Canada",
                    officialName = "Canada",
                    flagUrl = "https://flagcdn.com/w320/ca.png",
                    capital = "Ottawa",
                    region = "Americas",
                    subregion = "North America",
                    population = 38005238,
                    area = 9984670.0,
                    languages = listOf("English", "French"),
                    currencies = listOf("Canadian dollar ($)"),
                    timezones = listOf("UTC-08:00", "UTC-07:00", "UTC-06:00", "UTC-05:00", "UTC-04:00", "UTC-03:30"),
                    borders = listOf("USA"),
                    mapUrl = "https://goo.gl/maps/jmEVLugreeqiZXxbA",
                ),
                Country(
                    code = "JP",
                    name = "Japan",
                    officialName = "Japan",
                    flagUrl = "https://flagcdn.com/w320/jp.png",
                    capital = "Tokyo",
                    region = "Asia",
                    subregion = "Eastern Asia",
                    population = 125836021,
                    area = 377930.0,
                    languages = listOf("Japanese"),
                    currencies = listOf("Japanese yen (¥)"),
                    timezones = listOf("UTC+09:00"),
                    borders = emptyList(),
                    mapUrl = "https://goo.gl/maps/NGTLSCSrA8bMrvnX9",
                ),
                Country(
                    code = "FR",
                    name = "France",
                    officialName = "French Republic",
                    flagUrl = "https://flagcdn.com/w320/fr.png",
                    capital = "Paris",
                    region = "Europe",
                    subregion = "Western Europe",
                    population = 67391582,
                    area = 551695.0,
                    languages = listOf("French"),
                    currencies = listOf("Euro (€)"),
                    timezones =
                        listOf(
                            "UTC-10:00",
                            "UTC-09:30",
                            "UTC-09:00",
                            "UTC-08:00",
                            "UTC-04:00",
                            "UTC-03:00",
                            "UTC+01:00",
                            "UTC+02:00",
                            "UTC+03:00",
                            "UTC+04:00",
                            "UTC+05:00",
                            "UTC+10:00",
                            "UTC+11:00",
                            "UTC+12:00",
                        ),
                    borders = listOf("AND", "BEL", "DEU", "ITA", "LUX", "MCO", "ESP", "CHE"),
                    mapUrl = "https://goo.gl/maps/g7QxxSFsWyTPKuzd7",
                ),
            )
    }
}
