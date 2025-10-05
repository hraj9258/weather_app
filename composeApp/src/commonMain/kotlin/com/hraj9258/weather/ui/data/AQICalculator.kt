package com.hraj9258.weather.ui.data


// --- Enum for AQI Standards --
enum class AQIStandard {
    US_EPA, UK_DEFRA, INDIA_CPCB, CHINA_MEE, EU_CAQI
}

data class AirQualityData(
    val co: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double
)

// --- Mapping regions to standards ---
fun regionToAQIStandard(region: String): AQIStandard {
    return when (region.uppercase()) {
        "US", "USA", "AMERICA" -> AQIStandard.US_EPA
        "UK", "GB", "UNITED KINGDOM", "LONDON" -> AQIStandard.UK_DEFRA
        "INDIA", "IN" -> AQIStandard.INDIA_CPCB
        "CHINA", "CN" -> AQIStandard.CHINA_MEE
        "EU", "EUROPE", "FRANCE", "GERMANY", "ITALY" -> AQIStandard.EU_CAQI
        else -> AQIStandard.US_EPA
    }
}

fun calculateGlobalAQI(data: AirQualityData?, region: String): Float? {
    if (data == null) return null
    val standard = regionToAQIStandard(region)

    return when (standard) {
        AQIStandard.US_EPA -> calculateUS_AQI(data).toFloat()
        AQIStandard.UK_DEFRA -> calculateUK_DAQI(data)
        AQIStandard.INDIA_CPCB -> calculateIndia_AQI(data)
        AQIStandard.CHINA_MEE -> calculateChina_AQI(data)
        AQIStandard.EU_CAQI -> calculateEU_CAQI(data)
    }
}

fun getAQIColorHex(aqiInput: Int): String {
//    val aqi = aqiInput.toIntOrNull() ?: return "#2dbe8d" // Default to "Good" if invalid

    return when (aqiInput) {
        in 0..50 -> "#2dbe8d"    // Good
        in 51..100 -> "#f9cf5f"  // Satisfactory
        in 101..200 -> "#ff7676" // Moderate
        in 201..300 -> "#d46a6a" // Poor (optional)
        in 301..400 -> "#b94b4b" // Very Poor (optional)
        in 401..500 -> "#8c2f2f" // Severe (optional)
        else -> "#8c2f2f"        // Beyond range
    }
}

// --- US EPA AQI Calculation ---
fun calculateUS_AQI(data: AirQualityData): Int {
    val subIndices = listOf(
        calculateSubIndexUS("PM2.5", data.pm2_5),
        calculateSubIndexUS("PM10", data.pm10),
        calculateSubIndexUS("O3", data.o3),
        calculateSubIndexUS("CO", data.co),
        calculateSubIndexUS("NO2", data.no2),
        calculateSubIndexUS("SO2", data.so2)
    ).filter { it >= 0 }

    return subIndices.maxOrNull() ?: -1
}

// --- UK DEFRA DAQI (1-10) ---
fun calculateUK_DAQI(data: AirQualityData): Float {
    // simplified: pick highest US sub-index and map to 1-10 scale
    val usAqi = calculateUS_AQI(data)
    return ((usAqi / 500.0) * 10).coerceIn(1.0, 10.0).toFloat()
}

// --- India CPCB AQI (0-500) ---
fun calculateIndia_AQI(data: AirQualityData): Float {
    return calculateUS_AQI(data).toFloat()
}

// --- China MEE AQI (0-300) ---
fun calculateChina_AQI(data: AirQualityData): Float {
    val usAqi = calculateUS_AQI(data)
    return (usAqi / 500.0 * 300).coerceIn(0.0, 300.0).toFloat()
}

// --- EU CAQI (0-100) ---
fun calculateEU_CAQI(data: AirQualityData): Float {
    val usAqi = calculateUS_AQI(data)
    return (usAqi / 500.0 * 100).coerceIn(0.0, 100.0).toFloat()
}

// --- US sub-index function ---
fun calculateSubIndexUS(pollutant: String, concentration: Double): Int {
    val breakpoints = when (pollutant) {
        "PM2.5" -> listOf(
            Pair(0.0..12.0, 0..50),
            Pair(12.1..35.4, 51..100),
            Pair(35.5..55.4, 101..150),
            Pair(55.5..150.4, 151..200),
            Pair(150.5..250.4, 201..300),
            Pair(250.5..350.4, 301..400),
            Pair(350.5..500.4, 401..500)
        )
        "PM10" -> listOf(
            Pair(0.0..54.0, 0..50),
            Pair(55.0..154.0, 51..100),
            Pair(155.0..254.0, 101..150),
            Pair(255.0..354.0, 151..200),
            Pair(355.0..424.0, 201..300),
            Pair(425.0..504.0, 301..400),
            Pair(505.0..604.0, 401..500)
        )
        "O3" -> listOf(
            Pair(0.0..0.054, 0..50),
            Pair(0.055..0.070, 51..100),
            Pair(0.071..0.085, 101..150),
            Pair(0.086..0.105, 151..200),
            Pair(0.106..0.200, 201..300)
        )
        "CO" -> listOf(
            Pair(0.0..4.4, 0..50),
            Pair(4.5..9.4, 51..100),
            Pair(9.5..12.4, 101..150),
            Pair(12.5..15.4, 151..200),
            Pair(15.5..30.4, 201..300),
            Pair(30.5..40.4, 301..400),
            Pair(40.5..50.4, 401..500)
        )
        "NO2" -> listOf(
            Pair(0.0..53.0, 0..50),
            Pair(54.0..100.0, 51..100),
            Pair(101.0..360.0, 101..150),
            Pair(361.0..649.0, 151..200),
            Pair(650.0..1249.0, 201..300),
            Pair(1250.0..1649.0, 301..400),
            Pair(1650.0..2049.0, 401..500)
        )
        "SO2" -> listOf(
            Pair(0.0..35.0, 0..50),
            Pair(36.0..75.0, 51..100),
            Pair(76.0..185.0, 101..150),
            Pair(186.0..304.0, 151..200),
            Pair(305.0..604.0, 201..300),
            Pair(605.0..804.0, 301..400),
            Pair(805.0..1004.0, 401..500)
        )
        else -> return -1
    }

    for ((range, index) in breakpoints) {
        if (concentration in range) {
            val (bpLo, bpHi) = range.start to range.endInclusive
            val (iLo, iHi) = index.first to index.last
            return (((iHi - iLo) / (bpHi - bpLo)) * (concentration - bpLo) + iLo).toInt()
        }
    }
    return -1
}
