package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsraj.funkytalk.ui.theme.FunkyBorder
import com.itsraj.funkytalk.ui.theme.FunkyTextPrimary
import com.itsraj.funkytalk.ui.theme.FunkyTextSecondary
import com.itsraj.funkytalk.ui.theme.FunkyYellow
import com.itsraj.funkytalk.ui.theme.FunkyYellowSoft
import com.itsraj.funkytalk.ui.theme.FunkySurfaceElevated
import com.itsraj.funkytalk.ui.theme.Radii

enum class ChipStyle {
    FILLED,     // Solid yellow bg — for active/selected states
    OUTLINE,    // Yellow border, transparent bg — for learning languages
    GRAY        // Light gray bg — for interests/hobbies
}

@Composable
fun FunkyChip(
    text: String,
    style: ChipStyle = ChipStyle.FILLED,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        selected && style == ChipStyle.FILLED -> FunkyYellow
        selected && style == ChipStyle.OUTLINE -> FunkyYellowSoft
        style == ChipStyle.GRAY -> FunkySurfaceElevated
        style == ChipStyle.OUTLINE -> Color.Transparent
        else -> FunkyYellowSoft
    }
    val textColor = when {
        selected && style == ChipStyle.FILLED -> FunkyTextPrimary
        style == ChipStyle.GRAY -> FunkyTextSecondary
        else -> FunkyTextPrimary
    }
    val borderColor = when {
        style == ChipStyle.OUTLINE -> FunkyYellow
        else -> Color.Transparent
    }

    val shape = RoundedCornerShape(999.dp)
    val container = @Composable {
        Box(
            modifier = Modifier
                .clip(shape)
                .let { mod ->
                    if (borderColor != Color.Transparent)
                        mod.border(
                            1.5.dp, borderColor,
                            shape
                        )
                    else mod
                }
                .background(bgColor)
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            androidx.compose.material3.Text(
                text = text,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }

    if (onClick != null) {
        Box(
            modifier = modifier
                .clip(shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            container()
        }
    } else {
        Box(modifier = modifier) {
            container()
        }
    }
}
