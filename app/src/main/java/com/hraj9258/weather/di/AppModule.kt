package com.hraj9258.weather.di

import com.hraj9258.weather.core.data.HttpClientFactory
import com.hraj9258.weather.ui.data.api.WeatherApiService
import com.hraj9258.weather.ui.data.repository.WeatherRepositoryImpl
import com.hraj9258.weather.ui.domain.repository.WeatherRepository
import com.hraj9258.weather.ui.domain.usecase.GetWeatherUseCase
import com.hraj9258.weather.ui.presentation.WeatherViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<String>(qualifier = named("WeatherApiKey")) { getProperty("apiKey") }

    single{ HttpClientFactory.create() }

    single { WeatherApiService(client = get(),apiKey = get(qualifier = named("WeatherApiKey"))) }
    singleOf(::WeatherRepositoryImpl).bind<WeatherRepository>()
    single { GetWeatherUseCase(get()) }


    viewModelOf(::WeatherViewModel)
}
