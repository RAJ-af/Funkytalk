package com.itsraj.funkytalk.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FunkyColorScheme = lightColorScheme(
    primary = FunkyYellow,
    onPrimary = FunkyTextOnYellow,
    primaryContainer = FunkyYellowSoft,
    onPrimaryContainer = FunkyTextOnYellow,

    secondary = FunkyYellow,
    onSecondary = FunkyTextOnYellow,
    secondaryContainer = FunkyYellowLight,
    onSecondaryContainer = FunkyTextPrimary,

    tertiary = FunkyYellow,
    onTertiary = FunkyTextOnYellow,

    background = FunkyBackground,
    onBackground = FunkyTextPrimary,

    surface = FunkySurface,
    onSurface = FunkyTextPrimary,
    surfaceVariant = FunkySurfaceElevated,
    onSurfaceVariant = FunkyTextSecondary,

    outline = FunkyBorder,
    outlineVariant = FunkyBorderLight,

    error = FunkyError,
    onError = FunkySurface,
    errorContainer = FunkyError.copy(alpha = 0.08f),
    onErrorContainer = FunkyError,

    inverseSurface = FunkyTextPrimary,
    inverseOnSurface = FunkyBackground,
    surfaceTint = FunkyYellow
)

@Composable
fun FunkyTalkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FunkyColorScheme,
        typography = FunkyTypography,
        shapes = AppShapes.material,
        content = content
    )
}
