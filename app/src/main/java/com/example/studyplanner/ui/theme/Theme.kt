package com.example.studyplanner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Indigo,
    onPrimary = Color.White,
    primaryContainer = IndigoContainer,
    secondary = Teal,
    secondaryContainer = TealContainer,
    background = LightBackground,
    surface = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = IndigoLight,
    primaryContainer = IndigoDarkContainer,
    secondary = TealLight,
    secondaryContainer = TealDarkContainer,
    background = DarkBackground,
    surface = DarkSurface
)

@Composable
fun StudyPlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}