package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itsraj.funkytalk.ui.theme.*

@Composable
fun FunkyCard(
    modifier: Modifier = Modifier,
    shape: Shape = AppShapes.card,
    backgroundColor: Color = FunkySurface,
    border: BorderStroke? = BorderStroke(1.dp, FunkyBorderLight),
    padding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        border = border,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(padding)) {
            content()
        }
    }
}
