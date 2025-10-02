import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.hraj9258.weather"
    compileSdk = 36
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.hraj9258.weather"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "WEATHER_API_KEY", properties.getProperty("WEATHER_API_KEY"))
        }
        debug {
            buildConfigField("String", "WEATHER_API_KEY", properties.getProperty("WEATHER_API_KEY"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //AndroidX
    implementation(libs.bundles.androidX)
    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.tooling)

    // Koin
    implementation(libs.koin.androidx.compose)

    // Fused Location Provider
    implementation(libs.play.services.location)

    // Ktor
    implementation(libs.bundles.ktor)
}