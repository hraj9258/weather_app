package com.hraj9258.weather.ui.data.mappers

import com.hraj9258.weather.ui.data.AirQualityData
import com.hraj9258.weather.ui.data.dto.AirQualityDto
import com.hraj9258.weather.ui.data.dto.ForecastDayDto
import com.hraj9258.weather.ui.data.dto.WeatherDto
import com.hraj9258.weather.ui.domain.model.AirQualityDataUI
import com.hraj9258.weather.ui.domain.model.ForecastItem
import com.hraj9258.weather.ui.domain.model.Weather
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


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

@OptIn(ExperimentalTime::class)
fun ForecastDayDto.toForecastItem(): ForecastItem {
    // Parse the input date ("yyyy-MM-dd")
    val localDate = LocalDate.parse(this.date)

    // Determine isSelected based on today's date
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val isSelected = localDate == today

    // Day of week ("Mon")
    val dayOfWeek = localDate.dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() }.substring(0, 3)

    // Format date to "13 Feb"
    val outputFormat = LocalDate.Format {
        dayOfMonth()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
    }
    val formattedDate = outputFormat.format(localDate)


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
