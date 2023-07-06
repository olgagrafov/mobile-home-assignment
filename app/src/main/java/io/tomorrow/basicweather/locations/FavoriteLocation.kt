package io.tomorrow.basicweather.locations

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteLocation(
    val id: String,
    val name: String,
    val timezone: String,
    val temperature: String,
    val sunrise: String,
    val sunset: String
){
     var timesOfDay:String = ""
     var localTime:String = ""
}
