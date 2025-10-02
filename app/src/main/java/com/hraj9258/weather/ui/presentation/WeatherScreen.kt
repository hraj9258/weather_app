package com.hraj9258.weather.ui.presentation

import android.Manifest
import android.location.Location
import com.hraj9258.weather.ui.presentation.components.ActionBar
import com.hraj9258.weather.ui.presentation.components.AirQuality
import com.hraj9258.weather.ui.presentation.components.DailyForecast
import com.hraj9258.weather.core.presentation.theme.ColorBackground
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hraj9258.weather.core.data.service.LocationService
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import org.koin.androidx.compose.koinViewModel
import com.hraj9258.weather.ui.data.mappers.WeatherIconMapper
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hraj9258.weather.ui.presentation.components.WeeklyForecast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var lastLocation by remember { mutableStateOf<Location?>(null) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
            refreshing = true
    }

    suspend fun refreshWeather() {
            val locationService = LocationService(context)
            val location = locationService.getCurrentLocation()
            lastLocation = location
            location?.let { loc ->
                viewModel.handleIntent(WeatherIntent.LoadWeather(loc.latitude, loc.longitude))
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
