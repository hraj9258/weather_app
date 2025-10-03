package com.hraj9258.weather.ui.data.api

import com.hraj9258.weather.ui.data.model.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

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