package io.tomorrow.basicweather.locations

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteLocations(
    val locations: List<FavoriteLocation>
)
