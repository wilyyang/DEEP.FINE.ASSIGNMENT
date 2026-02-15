package com.deepfine.assignment.core.feature.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ColorSet.blue_1e9eff,
    onPrimary = ColorSet.white_ffffff,
    primaryContainer = ColorSet.sky_8fc5f2,
    onPrimaryContainer = ColorSet.white_ffffff,

    secondary = ColorSet.cyan_1ecdcd,
    onSecondary = ColorSet.white_ffffff,
    tertiary = ColorSet.navy_324155,
    onTertiary = ColorSet.white_ffffff,

    background = ColorSet.gray_f3f4f5,
    onBackground = ColorSet.black_303538,

    surface = ColorSet.white_ffffff,
    onSurface = ColorSet.black_333333,
    surfaceVariant = ColorSet.white_f7fafc,
    onSurfaceVariant = ColorSet.black_353a3d,

    surfaceContainer = ColorSet.gray_f2f4f5,

    outline = ColorSet.gray_e7e9ec,
    outlineVariant = ColorSet.gray_d7dbde
)

@Composable
fun DeepfineTheme(
    typography: Typography = baseTypography,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography
    ) {
        content()
    }
}