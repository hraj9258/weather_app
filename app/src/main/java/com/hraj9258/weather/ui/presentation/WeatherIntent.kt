package com.hraj9258.weather.ui.presentation

sealed class WeatherIntent {
    data class LoadWeather(val lat: Double, val lon: Double) : WeatherIntent()
}
