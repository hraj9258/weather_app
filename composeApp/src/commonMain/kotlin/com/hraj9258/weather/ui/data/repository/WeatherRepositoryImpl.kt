package com.hraj9258.weather.ui.data.repository

import com.hraj9258.weather.core.domain.DataError
import com.hraj9258.weather.core.domain.Result
import com.hraj9258.weather.core.domain.map
import com.hraj9258.weather.ui.data.api.WeatherApiService
import com.hraj9258.weather.ui.data.mappers.toWeather
import com.hraj9258.weather.ui.domain.model.Weather
import com.hraj9258.weather.ui.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Result<Weather, DataError.Remote> {
        return api.getCurrentWeather(lat, lon)
            .map {weatherDto ->
                weatherDto.toWeather()
            }
    }
}
