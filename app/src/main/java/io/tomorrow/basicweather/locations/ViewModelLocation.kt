package io.tomorrow.basicweather.locations


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.tomorrow.basicweather.utils.TimeTools
import kotlinx.coroutines.launch

class ViewModelLocation: ViewModel() {

    private val _location = MutableLiveData<List<FavoriteLocation>>()
    val location: LiveData<List<FavoriteLocation>> = _location

    private val _errorLocation = MutableLiveData<String>()
    val errorLocation: LiveData<String> = _errorLocation

    private val _itemLocation = MutableLiveData<ItemLocation>()
    val itemLocation: LiveData<ItemLocation> = _itemLocation

    private val _errorItem = MutableLiveData<String>()
    val errorItem: LiveData<String> = _errorItem

    @SuppressLint("SuspiciousIndentation")
    fun fetchLocation() {
        viewModelScope.launch {
            try {
//                var loc = FavoriteLocation( "ljndfgls49nsdfkgn9", "Yavne", "Asia/Jerusalem", "22°C",  "2023-07-05T04:00:00.000Z","2023-07-05T17:25:00.000Z")
//                var loc1 = FavoriteLocation( "ajndfgls49nsdfkgn1", "Yavne", "Asia/Jerusalem", "22°C",  "2023-07-05T04:00:00.000Z","2023-07-05T17:25:00.000Z")
//                var loc2 = FavoriteLocation( "bjndfgls49nsdfkgn2", "Yavne", "Asia/Jerusalem", "22°C",  "2023-07-05T04:00:00.000Z","2023-07-05T17:25:00.000Z")
//                val locations = listOf<FavoriteLocation>(loc,loc1,loc2)
                val locations = FavoriteLocationsApi.getFavoriteLocations().locations
                addIsNightProperties(locations)
                _location.value = locations
            } catch (e: Exception) {
                _errorLocation.value = "Failed to retrieve locations: \n ${e.toString()}"
            }
        }
    }

    fun fetchItemLocation(id: String) {
        viewModelScope.launch {
            try{
                //var item = ItemLocation("ljndfgls49nsdfkgn9", "Joker Place", "Asia/Jerusalem", "22°C", "2023-07-05T04:00:00.000Z", "2023-07-05T17:25:00.000Z", "25%", "Psadpdf", "5%", "NNW", 2)
                _itemLocation.value = FavoriteLocationsApi.getFavoriteItemLocations(id)
            }
            catch (e: Exception) {
                _errorItem.value = "Failed to retrieve item locations: \n  ${e.toString()}"
            }
        }
    }

    private suspend fun addIsNightProperties(locations: List<FavoriteLocation>) {
        for(loc in locations){
            val now = TimeTools.nowInUtc()
            loc.localTime = TimeTools.resolveLocalTime( now,loc.timezone)
            loc.timesOfDay =if (TimeTools.isTimestampBetweenLimits(loc.localTime, loc.sunrise, loc.sunset))  "night" else "day"
        }
    }
}

