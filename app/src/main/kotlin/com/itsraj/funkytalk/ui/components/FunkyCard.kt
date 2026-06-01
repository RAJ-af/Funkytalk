package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itsraj.funkytalk.ui.theme.AppShapes
import com.itsraj.funkytalk.ui.theme.Elevations
import com.itsraj.funkytalk.ui.theme.Sizes
import com.itsraj.funkytalk.ui.theme.FunkySurface

@Composable
fun FunkyCard(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    elevation: androidx.compose.ui.unit.Dp = Elevations.card,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier,
        shape = AppShapes.card,
        color = FunkySurface,
        tonalElevation = elevation,
        shadowElevation = elevation
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(Sizes.cardPaddingMd),
            content = content
        )
    }
}
