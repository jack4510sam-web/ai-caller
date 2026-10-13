package com.aicaller.app.core.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AiCallerColorScheme = darkColorScheme(
    primary = Accent, onPrimary = Ink, secondary = AccentDeep, onSecondary = Ink,
    background = Ink, onBackground = TextPrimary, surface = Surface, onSurface = TextPrimary,
    surfaceVariant = SurfaceElevated, onSurfaceVariant = TextSecondary,
    outline = DividerColor, error = Danger
)

@Composable
fun AiCallerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AiCallerColorScheme, typography = AiCallerTypography, content = content)
}
