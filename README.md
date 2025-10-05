# Weather App (Kotlin MultiPlatform • Compose MultiPlatform)

![App preview](https://github.com/user-attachments/assets/ca4ef541-c267-48bc-bdb0-e77769129318)

A modern Mobile Weather application built with Kotlin MultiPlatform and Compose MultiPlatform. The app fetches current conditions and a 7‑day forecast for the user’s location, including Air Quality Index (AQI) details, and presents them in a polished UI.

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
The project uses version catalogs.

- Kotlin MultiPlatform
- Compose MultiPlatform
- Koin
- Ktor
- Compass

## API & Configuration
The app uses WeatherAPI.com’s forecast endpoint:
- Base URL: http://api.weatherapi.com/v1/forecast.json
- Required parameters: key (API key), q (lat,lon), days=7, aqi=yes, alerts=no

To provide your API key, add the following to local.properties in the project root:

WEATHER_API_KEY="YOUR_WEATHERAPI_KEY_HERE"

The build system injects this into BuildConfig.WEATHER_API_KEY, which is then passed to Koin and used by WeatherApiService.


## Project Structure (high level)

This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


## Running the App
Permissions: The app requests location permission and uses Compass:geolocation to determine the current coordinates.

### Build and Run Android Application

1. Open the project in Android Studio (latest recommended).
2. In local.properties, set your WEATHER_API_KEY as shown above.
3. Sync Gradle.
4. Run on a device/emulator with location services enabled.

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

## Credits
- UI design and layout inspiration: mishamoovex / WeatherAppUi
  - https://github.com/mishamoovex/WeatherAppUi
- Weather data: https://www.weatherapi.com/


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

---