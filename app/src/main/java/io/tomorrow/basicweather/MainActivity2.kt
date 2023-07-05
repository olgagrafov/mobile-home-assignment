package io.tomorrow.basicweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.tomorrow.basicweather.locations.ViewModelLocation
import io.tomorrow.basicweather.ui.theme.BasicWeatherTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp



class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ViewModelLocation by viewModels()
        setContent {
            BasicWeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetData(viewModel)
                }
            }
        }
    }
}

@Composable
fun SetData(viewModel: ViewModelLocation) {

   val locations by viewModel.location.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchLocation()
    }

    if (locations.isEmpty()) {
        Text("Loading ..... ")
    } else {
        LazyColumn (modifier = Modifier.padding(5.dp)) {
            items(locations) { item ->
                Card(modifier = Modifier.padding(5.dp).fillMaxWidth()) {
                    Text(text = item.name)
                    Text(text = item.temperature)
                    Text(text = if (item.isNight)  "night" else "day")
                }
            }
        }
    }
}

