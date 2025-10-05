package com.hraj9258.weather.di

import com.hraj9258.weather.BuildConfig
import com.hraj9258.weather.core.data.HttpClientFactory
import com.hraj9258.weather.ui.data.api.WeatherApiService
import com.hraj9258.weather.ui.data.repository.WeatherRepositoryImpl
import com.hraj9258.weather.ui.domain.repository.WeatherRepository
import com.hraj9258.weather.ui.domain.usecase.GetWeatherUseCase
import com.hraj9258.weather.ui.presentation.WeatherViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val SharedModules = module {

//    single<String>(qualifier = named("WeatherApiKey")) { getProperty("apiKey") }
    single<String>(qualifier = named("WeatherApiKey")) { BuildConfig.WEATHER_API_KEY }

    single{ HttpClientFactory.create(get()) }

    single { WeatherApiService(httpClient = get(),apiKey = get(qualifier = named("WeatherApiKey"))) }
    singleOf(::WeatherRepositoryImpl).bind<WeatherRepository>()
    single { GetWeatherUseCase(get()) }

    viewModelOf(::WeatherViewModel)
}
