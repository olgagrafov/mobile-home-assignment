package io.tomorrow.basicweather.locations

import kotlinx.serialization.Serializable

@Serializable
data class ItemLocation(
    val id: String,
    val name: String,
    val timezone: String,
    val temperature: String,
    val sunrise: String,
    val sunset: String,
    val humidity: String,
    val description: String,
    val precipitationProbability: String,
    val windDirection: String,
    val fireIndex: Int
)
