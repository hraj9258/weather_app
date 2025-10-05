package com.hraj9258.weather.ui.domain.repository

import com.hraj9258.weather.core.domain.DataError
import com.hraj9258.weather.core.domain.Result
import com.hraj9258.weather.ui.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<Weather, DataError.Remote>
}
