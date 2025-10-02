package com.hraj9258.weather.ui.data.mappers

import com.hraj9258.weather.ui.data.AirQualityData
import com.hraj9258.weather.ui.data.model.AirQualityDto
import com.hraj9258.weather.ui.data.model.ForecastDayDto
import com.hraj9258.weather.ui.data.model.WeatherDto
import com.hraj9258.weather.ui.domain.model.AirQualityDataUI
import com.hraj9258.weather.ui.domain.model.ForecastItem
import com.hraj9258.weather.ui.domain.model.Weather
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun WeatherDto.toWeather() : Weather{
    return Weather(
        city = location.name,
        country = location.country,
        temperatureC = current.temp_c,
        weatherDescription = current.condition.text,
        isDay = current.is_day == 1,
        iconUrl = "https:" + current.condition.icon,
        airQualityData = AirQualityDataUI(
            realFeel = "${current.feelslike_c}°",
            wind = "${current.wind_kph}km/h",
            so2 = current.airQuality?.so2 ?: -1f,
            rain = forecast.forecastDay[0].day.daily_chance_of_rain,
            uvIndex = current.uv,
            o3 = current.airQuality?.o3 ?: -1f
        ),
        weekForecast = forecast.forecastDay.map { it.toForecastItem() }
    )
}
fun AirQualityDto.toAirQualityData() : AirQualityData {
    return AirQualityData(
        co = co.toDouble(),
        no2 = no2.toDouble(),
        o3 = o3.toDouble(),
        so2 = so2.toDouble(),
        pm2_5 = pm2_5.toDouble(),
        pm10 = pm10.toDouble()
    )
}

fun ForecastDayDto.toForecastItem(): ForecastItem {
    // Parse the input date ("yyyy-MM-dd")
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(this.date, inputFormatter)

    // Determine isSelected based on today's date
    val isSelected = localDate.isEqual(LocalDate.now())

    // Day of week ("Mon")
    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

    // Format date to "13 Feb"
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
    val formattedDate = localDate.format(outputFormatter)

    // Format temperature
    val temperatureFormatted = "${this.day.avgtemp_c.toInt()}°"

    return ForecastItem(
        image = WeatherIconMapper.mapToDrawable(this.day.condition.text),
        dayOfWeek = dayOfWeek,
        date = formattedDate,
        temperature = temperatureFormatted,
        isSelected = isSelected
    )
}
