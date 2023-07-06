package io.tomorrow.basicweather

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val message by viewModel.errorLocation.observeAsState("Loading ..... ")

    LaunchedEffect(Unit) {
        viewModel.fetchLocation()
    }

    if (locations.isEmpty()) {
        Text( text = message, fontSize = 24.sp)
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
    var hasFetchedData by remember { mutableStateOf(false) }
    val error by viewModel.errorItem.observeAsState()

    error?.let { errorMessage ->
        Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column() {
            Text(text = item.name)
            Text(text = item.temperature)
            Text(text = item.localTime)
            Text(text = item.timesOfDay)
            Button(onClick = {
                isExpanded = !isExpanded
                if (isExpanded && !hasFetchedData) {
                    viewModel.fetchItemLocation(item.id)
                    hasFetchedData = true
                }
            }) {
                Text(text = if(isExpanded) "show more" else "show less")
            }
            if (isExpanded) {
                itemLocation?.let {
                    Divider()
                    Card(modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()){
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