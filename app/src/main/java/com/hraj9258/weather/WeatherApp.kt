package com.hraj9258.weather

import android.app.Application
import com.hraj9258.weather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(appModule)
            properties(mapOf("apiKey" to BuildConfig.WEATHER_API_KEY))
        }
    }
}
