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

    private val _itemLocation = MutableLiveData<ItemLocation>()
    val itemLocation: LiveData<ItemLocation> = _itemLocation

    fun fetchLocation() {
        viewModelScope.launch {
            try {
                val locations = FavoriteLocationsApi.getFavoriteLocations().locations
                 addIsNightProperties(locations)
                _location.value = locations
            } catch (e: Exception) {
                 throw Exception("Failed to retrieve locations")
            }
        }
    }

    fun fetchItemLocation(id: String) {
        viewModelScope.launch {
            try{
                _itemLocation.value =  FavoriteLocationsApi.getFavoriteItemLocations(id)
            }
            catch (e: Exception) {
                throw Exception("Failed to retrieve item locations")
            }
        }
    }

    private suspend fun addIsNightProperties(locations: List<FavoriteLocation>) {
        for(loc in locations){
            val now = TimeTools.nowInUtc()
            loc.localTime = TimeTools.resolveLocalTime( now,loc.timezone)
            loc.isNight = TimeTools.isTimestampBetweenLimits(loc.localTime, loc.sunrise, loc.sunset)
        }
    }
}

