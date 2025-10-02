package com.hraj9258.weather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hraj9258.weather.ui.presentation.WeatherScreen
import com.hraj9258.weather.core.presentation.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var permissionRequested by remember { mutableStateOf(false) }
            var hasPermission by remember { mutableStateOf(false) }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                hasPermission = granted
            }

            LaunchedEffect(Unit) {
                if (!permissionRequested) {
                    permissionRequested = true
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            if (!hasPermission && permissionRequested) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Location permission required to fetch weather data.",
                        modifier = Modifier.padding(32.dp)
                    )
                }
            } else {
                WeatherAppTheme {
                    App(
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier
) {
    WeatherScreen(modifier = modifier)
}
