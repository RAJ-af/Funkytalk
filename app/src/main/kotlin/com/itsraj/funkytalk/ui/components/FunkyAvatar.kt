package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.AppShapes
import com.itsraj.funkytalk.ui.theme.Elevations
import com.itsraj.funkytalk.ui.theme.FunkyYellow
import com.itsraj.funkytalk.ui.theme.Sizes

@Composable
fun FunkyAvatar(
    url: String?,
    username: String?,
    size: Dp = Sizes.avatarXl,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(size),
        shape = AppShapes.avatar,
        color = Color.White,
        tonalElevation = if (size >= Sizes.avatarXl) Elevations.card else 0.dp,
        shadowElevation = if (size >= Sizes.avatarXl) Elevations.card else 0.dp
    ) {
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FunkyYellow.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (username?.take(2) ?: "?").uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.35f).sp,
                    color = FunkyYellow
                )
            }
        }
    }
}
