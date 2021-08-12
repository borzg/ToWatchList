package com.borzg.towatchlist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.borzg.towatchlist.R

private val LightColors = lightColors(
    primary = Color(0xffff9100),
    primaryVariant = Color(0xffffc246),
    onPrimary = Color(0xffffc246),
    background = Color.White,
    secondary = Color(0xff8c9eff),
    secondaryVariant = Color(0xff5870cb)
)

private val DarkColors = darkColors(
    primary = Color(0xffff9100),
    primaryVariant = Color(0xffffc246),
    onPrimary = Color(0xffffc246),
    background = Color.White,
    secondary = Color(0xff8c9eff),
    secondaryVariant = Color(0xff5870cb)
)

@Composable
fun ToWatchListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    
    MaterialTheme(
        colors = colors,
        typography = Typography(
            defaultFontFamily = FontFamily(
                Font(R.font.montserrat_regular)
            ),
        ),
        content = content
    )
}