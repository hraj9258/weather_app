package com.hraj9258.weather.ui.domain.model

import androidx.annotation.DrawableRes

data class Weather(
    val city: String,
    val country: String,
    val temperatureC: Double,
    val weatherDescription: String,
    val isDay: Boolean,
    val iconUrl: String,
    val airQualityData: AirQualityDataUI,
    val weekForecast: List<ForecastItem>
)

data class AirQualityDataUI(
    val realFeel: String,
    val wind: String,
    val so2: Float,
    val rain: Float,
    val uvIndex: Float,
    val o3: Float,
)

data class DayForecast(
    val date: String,
    val maxTempC: Double,
    val minTempC: Double,
    val condition: String,
    val iconUrl: String
)

data class ForecastItem(
    @DrawableRes val image: Int,
    val dayOfWeek: String,
    val date: String,
    val temperature: String,
    val isSelected: Boolean = false
)