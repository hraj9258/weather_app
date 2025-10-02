package com.hraj9258.weather.ui.data.api

import com.hraj9258.weather.ui.data.model.WeatherDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class WeatherApiService(
    private val client: HttpClient,
    private val apiKey: String
) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherDto {
        val response: HttpResponse = client.get(
            "http://api.weatherapi.com/v1/forecast.json"
        ) {
            parameter("key", apiKey)
            parameter("q", "$lat,$lon")
            parameter("days", 7)
            parameter("aqi", "yes")
            parameter("alerts", "no")
        }
        return response.body()
    }
}