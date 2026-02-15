package com.deepfine.assignment.core.feature.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.deepfine.assignment.core.feature.R

private val sdGothicNeo = FontFamily(
    Font(R.font.apple_sd_gothic_neo_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.apple_sd_gothic_neo_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.apple_sd_gothic_neo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.apple_sd_gothic_neo_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.apple_sd_gothic_neo_bold, FontWeight.Bold, FontStyle.Normal)
)

val baseTypography = Typography(
    // Display
    displayLarge = TextStyle(
        lineHeight = 26.8.sp,
        fontSize = 24.4.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        lineHeight = 24.1.sp,
        fontSize = 21.9.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = TextStyle(
        lineHeight = 22.9.sp,
        fontSize = 20.8.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),

    // Headline
    headlineLarge = TextStyle(
        lineHeight = 23.3.sp,
        fontSize = 21.2.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        lineHeight = 21.0.sp,
        fontSize = 19.1.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        lineHeight = 22.8.sp,
        fontSize = 20.8.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),

    // Title
    titleLarge = TextStyle(
        lineHeight = 22.2.sp,
        fontSize = 20.2.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        lineHeight = 18.7.sp,
        fontSize = 17.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        lineHeight = 17.8.sp,
        fontSize = 16.2.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.SemiBold
    ),

    // Body
    bodyLarge = TextStyle(
        lineHeight = 18.sp,
        fontSize = 15.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        lineHeight = 16.8.sp,
        fontSize = 14.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        lineHeight = 15.6.sp,
        fontSize = 13.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),

    // Label
    labelLarge = TextStyle(
        lineHeight = 15.6.sp,
        fontSize = 13.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    labelMedium = TextStyle(
        lineHeight = 13.sp,
        fontSize = 10.8.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    labelSmall = TextStyle(
        lineHeight = 8.8.sp,
        fontSize = 7.3.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    )
)