package com.example.moviecatalog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Orange200,
    primaryVariant = Orange200,
    secondary = Grey200,

    background = Black200,
    onSurface = Grey200

)


private val LightColorPalette = lightColors(
    primary = Orange200,
    primaryVariant = Orange200,
    secondary = Grey200,


    background = Black200,
    onSurface = Grey200,


)

@Composable
fun MovieCatalogTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}