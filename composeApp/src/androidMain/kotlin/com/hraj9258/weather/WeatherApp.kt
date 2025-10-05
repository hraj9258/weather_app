package com.hraj9258.weather

import android.app.Application
import com.hraj9258.weather.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@WeatherApp)
        }
//        startKoin {
//            modules(appModule)
//            properties(mapOf("apiKey" to BuildConfig.WEATHER_API_KEY))
//        }
    }
}