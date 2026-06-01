package com.itsraj.funkytalk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── Typography Scale ──────────────────────────────────────
// Uses FontFamily.Default (Roboto) — swap FontFamily when custom fonts are added
private val Default = FontFamily.Default

object AppTextStyle {
    // Hero / Display (screen titles, large username)
    val displayLarge = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Black,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    )
    val displayMedium = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.3).sp
    )
    val displaySmall = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.2).sp
    )

    // Headlines (section headers)
    val headline = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    )

    // Titles (card titles, usernames)
    val titleLarge = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 22.sp
    )
    val titleMedium = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp
    )
    val titleSmall = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    // Body (content paragraphs, captions)
    val body = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )
    val bodySmall = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
    val caption = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )

    // Labels (chips, badges, buttons)
    val label = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
    val labelSmall = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
    val labelTiny = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
}

// ─── Material3 Typography Mapping ──────────────────────────
val FunkyTypography = Typography(
    displayLarge = AppTextStyle.displayLarge,
    displayMedium = AppTextStyle.displayMedium,
    displaySmall = AppTextStyle.displaySmall,
    headlineLarge = AppTextStyle.headline,
    headlineMedium = AppTextStyle.titleLarge,
    headlineSmall = AppTextStyle.titleMedium,
    titleLarge = AppTextStyle.titleLarge,
    titleMedium = AppTextStyle.titleMedium,
    titleSmall = AppTextStyle.titleSmall,
    bodyLarge = AppTextStyle.body,
    bodyMedium = AppTextStyle.bodySmall,
    bodySmall = AppTextStyle.caption,
    labelLarge = AppTextStyle.label,
    labelMedium = AppTextStyle.labelSmall,
    labelSmall = AppTextStyle.labelTiny
)
