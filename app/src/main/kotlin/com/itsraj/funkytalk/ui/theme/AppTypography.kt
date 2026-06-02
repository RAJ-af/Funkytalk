package com.itsraj.funkytalk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system default font as Inter is not in the project resources.
// We will focus on Light and Medium weights for the "thin" modern look.
private val Default = FontFamily.Default

object AppTextStyle {
    // Hero / Display (large username)
    val displayLarge = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    )

    // Headlines (section headers)
    val headline = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.2).sp
    )

    // Titles (usernames in lists)
    val titleLarge = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 22.sp
    )

    val titleMedium = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp
    )

    // Body (content paragraphs)
    val body = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Light,
        fontSize = 15.sp,
        lineHeight = 20.sp
    )

    val bodySmall = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp,
        lineHeight = 18.sp
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

    val caption = TextStyle(
        fontFamily = Default,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
}

val FunkyTypography = Typography(
    displayLarge = AppTextStyle.displayLarge,
    headlineLarge = AppTextStyle.headline,
    headlineMedium = AppTextStyle.titleLarge,
    headlineSmall = AppTextStyle.titleMedium,
    titleLarge = AppTextStyle.titleLarge,
    titleMedium = AppTextStyle.titleMedium,
    titleSmall = AppTextStyle.titleMedium.copy(fontSize = 14.sp),
    bodyLarge = AppTextStyle.body,
    bodyMedium = AppTextStyle.bodySmall,
    bodySmall = AppTextStyle.caption,
    labelLarge = AppTextStyle.label,
    labelMedium = AppTextStyle.labelSmall,
    labelSmall = AppTextStyle.labelSmall.copy(fontSize = 10.sp)
)
