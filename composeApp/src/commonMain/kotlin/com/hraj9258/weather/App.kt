package com.hraj9258.weather


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.hraj9258.weather.ui.presentation.WeatherScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        WeatherScreen()
    }
}