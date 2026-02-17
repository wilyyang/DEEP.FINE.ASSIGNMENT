package com.deepfine.assignment.core.feature.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ColorSet.blue_2735ae,
    onPrimary = ColorSet.white_ffffff,

    surface = ColorSet.white_ffffff,
    onSurface = ColorSet.black_000000,
    surfaceVariant = ColorSet.white_ffffff,
    // 텍스트 필드 힌트 텍스트 기본색
    onSurfaceVariant = ColorSet.gray_999999,

    outline = ColorSet.gray_dddddd,
    error = ColorSet.red_ff5252,
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