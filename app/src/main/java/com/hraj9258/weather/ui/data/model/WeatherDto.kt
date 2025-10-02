package com.hraj9258.weather.ui.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto
)

@Serializable
data class LocationDto(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerialName("localtime") val localTime: String
)

@Serializable
data class WeatherConditionDto(
    val text: String,
    val icon: String,
    val code: Int
)

@Serializable
data class CurrentDto(
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    @SerialName("condition") val condition: WeatherConditionDto,
    val wind_kph: Float,
    val feelslike_c: Float,
    val uv: Float,
    @SerialName("air_quality") val airQuality: AirQualityDto? = null
)

@Serializable
data class AirQualityDto(
    val co: Float,
    val no2: Float,
    val o3: Float,
    val so2: Float,
    val pm2_5: Float,
    val pm10: Float,
    @SerialName("us-epa-index") val usEpaIndex: Int,
    @SerialName("gb-defra-index") val gbDefraIndex: Int
)

@Serializable
data class ForecastDto(
    @SerialName("forecastday") val forecastDay: List<ForecastDayDto>
)

@Serializable
data class ForecastDayDto(
    val date: String,
    val day: DayDto
)

@Serializable
data class DayDto(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val avgtemp_c: Float,
    val daily_chance_of_rain: Float,
    val condition: WeatherConditionDto,
)
