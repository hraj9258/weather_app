package com.hraj9258.weather.ui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hraj9258.weather.core.presentation.ShimmerEffect
import com.hraj9258.weather.core.presentation.theme.ColorGradient1
import com.hraj9258.weather.core.presentation.theme.ColorGradient2
import com.hraj9258.weather.core.presentation.theme.ColorGradient3
import com.hraj9258.weather.core.presentation.theme.ColorTextSecondary
import com.hraj9258.weather.core.presentation.theme.ColorTextSecondaryVariant
import com.hraj9258.weather.core.presentation.theme.ColorWindForecast
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.ic_frosty
import weatherapp.composeapp.generated.resources.ic_wind
import weatherapp.composeapp.generated.resources.img_clouds
import weatherapp.composeapp.generated.resources.img_cloudy
import weatherapp.composeapp.generated.resources.img_sub_rain
import weatherapp.composeapp.generated.resources.img_sun
import kotlin.math.roundToInt

@Composable
fun DailyForecast(
    temperatureC: Double = -1.0,
    description: String = "",
    isDay: Boolean = true,
    iconRes: DrawableResource = Res.drawable.img_sub_rain,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        CardBackground(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 24.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading) {
                    ShimmerEffect(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .padding(all = 32.dp)
                            .size(112.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 4.dp)
                    )
                }

                Spacer(Modifier.weight(1f))

                ForecastValue(
                    modifier = Modifier.padding(end = 24.dp),
                    degree = temperatureC.roundToInt().toString(),
                    description = "Feels like $temperatureC°C",
                    isLoading = isLoading
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 24.dp)
                ) {
                    if (isLoading) {
                        ShimmerEffect(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(0.7f) // Adjust fraction as needed
                                .clip(RoundedCornerShape(50))
                        )
                        Spacer(Modifier.height(4.dp)) // A small gap
                        ShimmerEffect(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(0.5f) // Adjust fraction as needed
                                .clip(RoundedCornerShape(50))
                        )
                    } else {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.titleLarge,
                            color = ColorTextSecondary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.basicMarquee()
                        )
                        Text(
                            text = if (isDay) "Today" else "Tonight",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ColorTextSecondaryVariant,
                        )
                    }
                }
                WindForecastImage()
            }
        }
    }
}

@Composable
private fun CardBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.5f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(32.dp)
            )
    )
}

@Composable
private fun ForecastValue(
    modifier: Modifier = Modifier,
    degree: String = "21",
    description: String = "Feels like 26°",
    isLoading: Boolean = false
) {
    if (isLoading) {
        ShimmerEffect(
            modifier = modifier
                .height(100.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(24.dp))
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = degree,
                    letterSpacing = 0.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            0f to Color.White,
                            1f to Color.White.copy(alpha = 0.3f)
                        ),
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black
                    ),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "°",
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            0f to Color.White,
                            1f to Color.White.copy(alpha = 0.3f)
                        ),
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = ColorTextSecondaryVariant
            )
        }
    }
}

@Composable
private fun WindForecastImage(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_frosty),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
        Icon(
            painter = painterResource(Res.drawable.ic_wind),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
    }
}

@Preview
@Composable
private fun DailyForecastPreview() {
    MaterialTheme {
        DailyForecast(
            temperatureC = 21.0,
            description = "Sunny",
            isDay = true,
            iconRes = Res.drawable.img_sub_rain,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }

}

@Preview
@Composable
private fun DailyForecastPreviewLoading() {
    MaterialTheme {
        DailyForecast(
            isLoading = true,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }

}

