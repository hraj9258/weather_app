# Weather App (Android • Jetpack Compose)

![App preview](https://github.com/user-attachments/assets/ca4ef541-c267-48bc-bdb0-e77769129318)

A modern Android Weather application built with Kotlin and Jetpack Compose. The app fetches current conditions and a 7‑day forecast for the user’s location, including Air Quality Index (AQI) details, and presents them in a polished UI.

Note on UI: The UI design and much of the Compose layout structure are adapted from the excellent work by mishamoovex. Original UI repository: https://github.com/mishamoovex/WeatherAppUi


## Features
- Current weather for the user’s location
- 7‑day forecast (WIP)
- Air Quality (AQI) details and basic interpretation
- Clean architecture with MVI, UseCases, and Repository layers
- Dependency Injection with Koin
- Kotlin Coroutines and Flows (where applicable)
- Network calls via Ktor


## Tech Stack
- Language: Kotlin 2.2.20
- UI: Jetpack Compose (Compose BOM 2025.09.01) and Material 3
- DI: Koin 4.1.1 (androidx-compose)
- Networking: Ktor 3.3.0 (CIO, Content Negotiation, Logging, Kotlinx JSON)
- Location: Google Play Services Location 21.3.0
- Android Gradle Plugin: 8.13.0
- Min SDK 28, Target SDK 35, Compile SDK 36


## Dependencies
The project uses version catalogs. Key libraries are declared in gradle/libs.versions.toml and referenced in app/build.gradle.kts.

- AndroidX
  - androidx.core:core-ktx (1.17.0)
  - androidx.lifecycle:lifecycle-runtime-ktx (2.9.4)
  - androidx.compose.material3:material3
- Jetpack Compose (managed by BOM 2025.09.01)
  - androidx.compose.ui:ui
  - androidx.compose.ui:ui-graphics
  - androidx.compose.ui:ui-tooling, ui-tooling-preview
  - androidx.activity:activity-compose (1.11.0)
  - androidx.constraintlayout:constraintlayout-compose (1.1.1)
- Dependency Injection
  - io.insert-koin:koin-androidx-compose (4.1.1)
- Networking (Ktor 3.3.0)
  - io.ktor:ktor-client-core
  - io.ktor:ktor-client-cio
  - io.ktor:ktor-client-content-negotiation
  - io.ktor:ktor-client-logging
  - io.ktor:ktor-serialization-kotlinx-json
- Location
  - com.google.android.gms:play-services-location (21.3.0)

Plugins (in settings via version catalogs):
- com.android.application (8.13.0)
- org.jetbrains.kotlin.android (2.2.20)
- org.jetbrains.kotlin.plugin.compose (2.2.20)
- org.jetbrains.kotlin.plugin.serialization (2.2.20)


## API & Configuration
The app uses WeatherAPI.com’s forecast endpoint:
- Base URL: http://api.weatherapi.com/v1/forecast.json
- Required parameters: key (API key), q (lat,lon), days=7, aqi=yes, alerts=no

To provide your API key, add the following to local.properties in the project root:

WEATHER_API_KEY="YOUR_WEATHERAPI_KEY_HERE"

The build system injects this into BuildConfig.WEATHER_API_KEY, which is then passed to Koin and used by WeatherApiService.


## Project Structure (high level)
- app/src/main/java/com/hraj9258/weather
  - WeatherApp.kt – Application class that boots Koin
  - di/ – Koin modules (AppModule)
  - core/ – Shared core pieces (HTTP client factory, theme)
  - ui/ – Feature layer
    - data/ – Data sources (WeatherApiService, mappers, DTOs, repository impl)
    - domain/ – Models, repository contract, use case (GetWeatherUseCase)
    - presentation/ – ViewModel (WeatherViewModel), intents/state, and Compose screens


## Running the App
1. Open the project in Android Studio (Giraffe+ or latest recommended).
2. In local.properties, set your WEATHER_API_KEY as shown above.
3. Sync Gradle.
4. Run on a device/emulator with location services enabled.

Permissions: The app requests location permission and uses Fused Location Provider to determine the current coordinates.


## Credits
- UI design and layout inspiration: mishamoovex / WeatherAppUi
  - https://github.com/mishamoovex/WeatherAppUi
- Weather data: https://www.weatherapi.com/


## License
This repository is for educational/demo purposes. If you reuse the UI, please follow the license and attribution guidelines of the original UI repository linked above.
