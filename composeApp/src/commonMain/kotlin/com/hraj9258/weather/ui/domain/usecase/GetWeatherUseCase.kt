package com.hraj9258.weather.ui.domain.usecase

import com.hraj9258.weather.core.domain.DataError
import com.hraj9258.weather.core.domain.Result
import com.hraj9258.weather.ui.domain.model.Weather
import com.hraj9258.weather.ui.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<Weather, DataError.Remote> = repository.getWeather(lat, lon)
}
