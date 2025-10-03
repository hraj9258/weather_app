package com.hraj9258.weather.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hraj9258.weather.ui.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun handleIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.LoadWeather -> loadWeather(intent.lat, intent.lon)
        }
    }

    private fun loadWeather(lat: Double, lon: Double) {
        _state.value = WeatherState.Loading
        viewModelScope.launch {
            try {
                val weather = getWeatherUseCase(lat, lon)
                _state.value = WeatherState.Success(weather)
            } catch (e: Exception) {
                _state.value = WeatherState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
