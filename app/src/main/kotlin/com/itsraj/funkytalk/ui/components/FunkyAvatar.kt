package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.FunkyBorder
import com.itsraj.funkytalk.ui.theme.Sizes

@Composable
fun FunkyAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = Sizes.avatarMd,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .then(
                if (borderWidth > 0.dp) Modifier.border(borderWidth, borderColor, CircleShape)
                else Modifier
            )
    ) {
        AsyncImage(
            model = imageUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
            contentDescription = "Avatar",
            modifier = Modifier.size(size).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
