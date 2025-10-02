package com.hraj9258.weather.ui.presentation.components

import com.hraj9258.weather.R
import com.hraj9258.weather.core.presentation.theme.ColorGradient1
import com.hraj9258.weather.core.presentation.theme.ColorGradient2
import com.hraj9258.weather.core.presentation.theme.ColorGradient3
import com.hraj9258.weather.core.presentation.theme.ColorTextSecondary
import com.hraj9258.weather.core.presentation.theme.ColorTextSecondaryVariant
import com.hraj9258.weather.core.presentation.theme.ColorWindForecast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.annotation.DrawableRes
import androidx.compose.foundation.basicMarquee

@Composable
fun DailyForecast(
    temperatureC: Double,
    description: String,
    isDay: Boolean,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (forecastImage, forecastValue, windImage, title, descriptionRef, background) = createRefs()

        CardBackground(
            modifier = Modifier.constrainAs(background) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = descriptionRef.bottom,
                    topMargin = 24.dp
                )
                height = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(175.dp)
                .constrainAs(forecastImage) {
                    start.linkTo(anchor = parent.start, margin = 4.dp)
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = description,
            style = MaterialTheme.typography.titleLarge,
            color = ColorTextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .basicMarquee()
                .constrainAs(title) {
                    start.linkTo(anchor = parent.start, margin = 24.dp)
                    top.linkTo(anchor = forecastImage.bottom)
                }

        )

        Text(
            text = if (isDay) "Today" else "Tonight",
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant,
            modifier = Modifier
                .constrainAs(descriptionRef) {
                    start.linkTo(anchor = title.start)
                    top.linkTo(anchor = title.bottom)
                }
                .padding(bottom = 24.dp)
        )

        ForecastValue(
            modifier = Modifier.constrainAs(forecastValue) {
                end.linkTo(anchor = parent.end, margin = 24.dp)
                top.linkTo(forecastImage.top)
                bottom.linkTo(forecastImage.bottom)
            },
            degree = String.format("%.0f", temperatureC),
            description = "Feels like $temperatureC°C"
        )

        WindForecastImage(
            modifier = Modifier.constrainAs(windImage) {
                linkTo(
                    top = title.top,
                    bottom = title.bottom
                )
                end.linkTo(anchor = parent.end, margin = 24.dp)
            }
        )
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
    description: String = "Feels like 26°"
) {
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

@Composable
private fun WindForecastImage(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_frosty),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
        Icon(
            painter = painterResource(R.drawable.ic_wind),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
    }
}


