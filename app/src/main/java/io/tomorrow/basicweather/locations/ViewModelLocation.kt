package io.tomorrow.basicweather.locations


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.tomorrow.basicweather.utils.TimeTools
import kotlinx.coroutines.launch

class ViewModelLocation: ViewModel() {

    private val _location = MutableLiveData<List<FavoriteLocation>>()
    val location: LiveData<List<FavoriteLocation>> = _location

    fun fetchLocation() {
        viewModelScope.launch {
            try {
                val locations = FavoriteLocationsApi.getFavoriteLocations().locations//listOf<FavoriteLocation>(f, f1, f2)
                 addIsNightProperties(locations)
                _location.value = locations
            } catch (e: Exception) {
                 throw Exception("Failed to retrieve locations")
            }
        }
    }

    private suspend fun addIsNightProperties(locations: List<FavoriteLocation>) {
        for(loc in locations){
            val now = TimeTools.nowInUtc()
            val localTime =  TimeTools.resolveLocalTime( now,loc.timezone)
            loc.isNight = TimeTools.isTimestampBetweenLimits(localTime, loc.sunrise, loc.sunset)
        }
    }
}

