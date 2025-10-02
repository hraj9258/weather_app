package com.hraj9258.weather.ui.domain.usecase

import com.hraj9258.weather.ui.domain.model.Weather
import com.hraj9258.weather.ui.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): Weather = repository.getWeather(lat, lon)
}
