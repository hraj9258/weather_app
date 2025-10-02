package com.hraj9258.weather.ui.data.repository

import com.hraj9258.weather.ui.data.api.WeatherApiService
import com.hraj9258.weather.ui.data.mappers.toWeather
import com.hraj9258.weather.ui.domain.model.Weather
import com.hraj9258.weather.ui.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        val dto = api.getCurrentWeather(lat, lon)
        return dto.toWeather()
    }
}
