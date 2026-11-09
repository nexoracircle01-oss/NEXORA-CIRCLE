package com.nexora.circle.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

fun buildColorScheme(s: ThemeSettings): ColorScheme {
    val accent = Color(s.accentArgb)
    val onAccent = if (accent.luminance() > 0.5f) Color.Black else Color.White
    return when (s.mode) {
        ThemeMode.LIGHT -> lightColorScheme(
            primary = accent,
            onPrimary = onAccent,
            primaryContainer = lerp(Color.White, accent, 0.18f),
            onPrimaryContainer = Color(0xFF16161C),
            secondary = accent,
            onSecondary = onAccent,
            background = Color(0xFFFAF9FB),
            onBackground = Color(0xFF16161C),
            surface = Color.White,
            onSurface = Color(0xFF16161C),
            surfaceVariant = Color(0xFFEDEBF0),
            onSurfaceVariant = Color(0xFF5E5C66),
            outline = Color(0xFFCFCCD6),
            surfaceContainerLow = Color(0xFFF5F3F7),
            surfaceContainer = Color(0xFFF1EFF4),
            surfaceContainerHigh = Color(0xFFEDEBF0),
            surfaceContainerHighest = Color(0xFFE8E6EC)
        )

        ThemeMode.DARK -> darkColorScheme(
            primary = accent,
            onPrimary = onAccent,
            primaryContainer = lerp(Color(0xFF16161C), accent, 0.28f),
            onPrimaryContainer = Color(0xFFF2F2F6),
            secondary = accent,
            onSecondary = onAccent,
            background = Color(0xFF0E0E12),
            onBackground = Color(0xFFECECF1),
            surface = Color(0xFF16161C),
            onSurface = Color(0xFFECECF1),
            surfaceVariant = Color(0xFF22222B),
            onSurfaceVariant = Color(0xFFA9A8B3),
            outline = Color(0xFF3A3A46),
            surfaceContainerLow = Color(0xFF13131A),
            surfaceContainer = Color(0xFF181820),
            surfaceContainerHigh = Color(0xFF1E1E27),
            surfaceContainerHighest = Color(0xFF252530)
        )

        ThemeMode.OLED -> darkColorScheme(
            primary = accent,
            onPrimary = onAccent,
            primaryContainer = lerp(Color.Black, accent, 0.28f),
            onPrimaryContainer = Color(0xFFF2F2F6),
            secondary = accent,
            onSecondary = onAccent,
            background = Color.Black,
            onBackground = Color(0xFFECECF1),
            surface = Color(0xFF0A0A0A),
            onSurface = Color(0xFFECECF1),
            surfaceVariant = Color(0xFF1A1A1A),
            onSurfaceVariant = Color(0xFFA0A0A8),
            outline = Color(0xFF2E2E36),
            surfaceContainerLow = Color(0xFF050505),
            surfaceContainer = Color(0xFF0A0A0A),
            surfaceContainerHigh = Color(0xFF121212),
            surfaceContainerHighest = Color(0xFF1A1A1A)
        )
    }
}

@Composable
fun NexoraTheme(settings: ThemeSettings, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    val systemFontScale = LocalConfiguration.current.fontScale
    val r = settings.cornerRadiusDp.toFloat()
    val shapes = Shapes(
        extraSmall = RoundedCornerShape((r * 0.3f).dp),
        small = RoundedCornerShape((r * 0.6f).dp),
        medium = RoundedCornerShape(r.dp),
        large = RoundedCornerShape((r * 1.2f).dp),
        extraLarge = RoundedCornerShape((r * 1.6f).dp)
    )
    CompositionLocalProvider(
        LocalDensity provides Density(density.density, systemFontScale * settings.fontScale)
    ) {
        MaterialTheme(
            colorScheme = buildColorScheme(settings),
            shapes = shapes,
            typography = Typography(),
            content = content
        )
    }
}
