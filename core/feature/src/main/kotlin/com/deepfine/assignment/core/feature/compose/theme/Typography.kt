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
    // ex) 로그인 화면 상단 타이틀
    titleMedium = TextStyle(
        lineHeight = 36.sp,
        fontSize = 30.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    // ex) 회원가입 화면 상단 타이틀
    titleSmall = TextStyle(
        lineHeight = 29.sp,
        fontSize = 24.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        lineHeight = 24.sp,
        fontSize = 20.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    // ex) 텍스트 입력, 버튼
    bodyMedium = TextStyle(
        lineHeight = 22.sp,
        fontSize = 18.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    // ex) 화면 상단 가이드글
    bodySmall = TextStyle(
        lineHeight = 18.sp,
        fontSize = 15.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    // ex) 텍스트 필드 상단 라벨
    labelLarge = TextStyle(
        lineHeight = 16.sp,
        fontSize = 13.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    ),
    // ex) 텍스트 필드 하단 안내
    labelMedium = TextStyle(
        lineHeight = 14.sp,
        fontSize = 12.sp,
        fontFamily = sdGothicNeo,
        fontWeight = FontWeight.Normal
    )
)