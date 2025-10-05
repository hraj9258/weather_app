package com.hraj9258.weather.ui.data.api

import com.hraj9258.weather.core.data.safeCall
import com.hraj9258.weather.core.domain.DataError
import com.hraj9258.weather.core.domain.Result
import com.hraj9258.weather.ui.data.dto.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiService(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<WeatherDto, DataError.Remote> {
        return safeCall<WeatherDto>{
            httpClient.get(
                "https://api.weatherapi.com/v1/forecast.json"
            ) {
                parameter("key", apiKey)
                parameter("q", "$lat,$lon")
                parameter("days", 7)
                parameter("aqi", "yes")
                parameter("alerts", "no")
            }
        }
    }
}