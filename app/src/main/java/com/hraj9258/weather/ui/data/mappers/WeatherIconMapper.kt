package com.hraj9258.weather.ui.data.mappers

import com.hraj9258.weather.R

object WeatherIconMapper {
    fun mapToDrawable(description: String): Int {
        return when {
            description.contains("sunny", ignoreCase = true) -> R.drawable.img_sun
            description.contains("rain", ignoreCase = true) -> R.drawable.img_rain
            description.contains("cloud", ignoreCase = true) -> R.drawable.img_clouds
            description.contains("thunder", ignoreCase = true) -> R.drawable.img_thunder
//            description.contains("snow", ignoreCase = true) -> R.drawable.img_sub_rain
//            description.contains("fog", ignoreCase = true) -> R.drawable.img_sub_rain
            else -> R.drawable.img_cloudy
        }
    }
}