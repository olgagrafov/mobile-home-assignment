package io.tomorrow.basicweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
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
import io.tomorrow.basicweather.locations.FavoriteLocation


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
                Card(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()) {
                    ClickableItem(item, viewModel)
                }
            }
        }
    }
}

@Composable
fun ClickableItem(item: FavoriteLocation, viewModel: ViewModelLocation) {
    val itemLocation by viewModel.itemLocation.observeAsState()
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(isExpanded) {
        viewModel.fetchItemLocation(item.id)
    }

    Box(
        modifier = Modifier
            .clickable { isExpanded = true }
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column() {
            Text(text = item.name)
            Text(text = item.temperature)
            Text(text = item.localTime)
            Text(text = if (item.isNight) "night" else "day")
            if (isExpanded) {
                itemLocation?.let {
                    Divider()
                    Card(modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable { isExpanded = false }) {
                        Text(
                            text = "description: ${it.description}",
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "humidity: ${it.humidity}",
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "precipitationProbability: ${it.precipitationProbability}",
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

    }
}


