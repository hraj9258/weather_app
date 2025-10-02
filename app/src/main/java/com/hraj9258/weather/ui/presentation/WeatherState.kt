package com.hraj9258.weather.ui.presentation

import com.hraj9258.weather.ui.domain.model.Weather

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: Weather) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
