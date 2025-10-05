package com.hraj9258.weather.ui.data.mappers

import org.jetbrains.compose.resources.DrawableResource
import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.img_clouds
import weatherapp.composeapp.generated.resources.img_cloudy
import weatherapp.composeapp.generated.resources.img_rain
import weatherapp.composeapp.generated.resources.img_sun
import weatherapp.composeapp.generated.resources.img_thunder

object WeatherIconMapper {
    fun mapToDrawable(description: String): DrawableResource {
        return when {
            description.contains("sunny", ignoreCase = true) -> Res.drawable.img_sun
            description.contains("rain", ignoreCase = true) -> Res.drawable.img_rain
            description.contains("cloud", ignoreCase = true) -> Res.drawable.img_clouds
            description.contains("thunder", ignoreCase = true) -> Res.drawable.img_thunder
//            description.contains("snow", ignoreCase = true) -> R.drawable.img_sub_rain
//            description.contains("fog", ignoreCase = true) -> R.drawable.img_sub_rain
            else -> Res.drawable.img_cloudy
        }
    }
}