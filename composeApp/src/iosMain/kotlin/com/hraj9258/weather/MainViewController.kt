package com.hraj9258.weather

import androidx.compose.ui.window.ComposeUIViewController
import com.hraj9258.weather.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}