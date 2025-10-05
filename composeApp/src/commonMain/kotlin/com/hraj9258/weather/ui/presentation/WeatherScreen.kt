package com.hraj9258.weather.ui.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hraj9258.weather.core.presentation.theme.ColorBackground
import com.hraj9258.weather.ui.data.mappers.WeatherIconMapper
import com.hraj9258.weather.ui.presentation.components.ActionBar
import com.hraj9258.weather.ui.presentation.components.AirQuality
import com.hraj9258.weather.ui.presentation.components.DailyForecast
import com.hraj9258.weather.ui.presentation.components.PreviewForecastData
import com.hraj9258.weather.ui.presentation.components.WeeklyForecast
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


data class Location(
    val latitude: Double,
    val longitude: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var lastLocation by remember { mutableStateOf<Location?>(null) }
    var refreshing by remember { mutableStateOf(false) }

    val geoLocation = remember { Geolocator.mobile() }

    LaunchedEffect(Unit) {
        refreshing = true
    }

    suspend fun refreshWeather() {
        when(val result = geoLocation.current()){
            is GeolocatorResult.Success -> {
                result.data.coordinates.let {
                    lastLocation = Location(
                        latitude = result.data.coordinates.latitude,
                        longitude = result.data.coordinates.longitude
                    )
                    viewModel.handleIntent(WeatherIntent.LoadWeather(it.latitude,it.longitude))
                }
            }
            is GeolocatorResult.Error -> {
                println("Error: ${result.message}")
            }
        }
        refreshing = false
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            refreshWeather()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        if (lastLocation == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(
                        "Fetching location...",
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        } else {
            PullToRefreshBox(
                isRefreshing = refreshing,
                onRefresh = {
                    refreshing = true
                },
                modifier = modifier
                    .padding(paddings)
            ) {
                when (state) {
                    is WeatherState.Loading -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            ActionBar(
                                isLoading = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            DailyForecast(
                                isLoading = true
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            AirQuality(
                                isLoading = true
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            WeeklyForecast(
                                isLoading = true
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Loading weather...",
                                    modifier = Modifier.padding(32.dp)
                                )
                            }
                        }
                    }
                    is WeatherState.Error -> {
                        val message = (state as WeatherState.Error).message
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Error: $message",
                                modifier = Modifier.padding(32.dp)
                            )
                        }
                    }
                    is WeatherState.Success -> {
                        val weather = (state as WeatherState.Success).weather
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            ActionBar(city = weather.city, country = weather.country)
                            Spacer(modifier = Modifier.height(12.dp))
                            DailyForecast(
                                temperatureC = weather.temperatureC,
                                description = weather.weatherDescription,
                                isDay = weather.isDay,
                                iconRes = WeatherIconMapper.mapToDrawable(weather.weatherDescription)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            AirQuality(
                                weather.airQualityData
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            WeeklyForecast( data = weather.weekForecast)
                        }
                    }
                }
            }
        }
    }
}