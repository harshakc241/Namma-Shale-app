package com.nammashaale.inventory.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF006C51),
    onPrimary = Color.White,
    secondary = Color(0xFF4F6358),
    tertiary = Color(0xFF3D6373),
    background = Color(0xFFF8FAF7),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFBA1A1A)
)

@Composable
fun NammaShaaleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
