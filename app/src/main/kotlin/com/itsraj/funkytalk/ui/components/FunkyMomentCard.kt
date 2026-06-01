package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.AppShapes
import com.itsraj.funkytalk.ui.theme.Elevations
import com.itsraj.funkytalk.ui.theme.FunkySurface
import com.itsraj.funkytalk.ui.theme.FunkyTextPrimary
import com.itsraj.funkytalk.ui.theme.FunkyTextSecondary
import com.itsraj.funkytalk.ui.theme.Radii
import com.itsraj.funkytalk.ui.theme.Sizes
import com.itsraj.funkytalk.ui.theme.Spacing

@Composable
fun FunkyMomentCard(
    username: String,
    avatarUrl: String?,
    content: String,
    imageUrl: String?,
    likeCount: Int,
    commentCount: Int,
    timestamp: String,
    onLike: () -> Unit,
    onComment: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        color = FunkySurface,
        tonalElevation = Elevations.card,
        shadowElevation = Elevations.card
    ) {
        Column(
            modifier = Modifier.padding(Sizes.cardPaddingMd)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FunkyAvatar(
                    url = avatarUrl,
                    username = username,
                    size = Sizes.avatarSm
                )
                Spacer(modifier = Modifier.width(Spacing.sm))
                Column {
                    Text(
                        text = username,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FunkyTextPrimary
                    )
                    Text(
                        text = timestamp,
                        fontSize = 11.sp,
                        color = FunkyTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.element))

            // Content
            Text(
                text = content,
                fontSize = 14.sp,
                color = FunkyTextPrimary,
                lineHeight = 20.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            // Image
            if (imageUrl != null) {
                Spacer(modifier = Modifier.height(Spacing.element))
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(Radii.lg)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(Spacing.element))

            // Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onLike)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        modifier = Modifier.size(18.dp),
                        tint = FunkyTextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = likeCount.toString(),
                        fontSize = 12.sp,
                        color = FunkyTextSecondary
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onComment)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Message,
                        contentDescription = "Comment",
                        modifier = Modifier.size(18.dp),
                        tint = FunkyTextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = commentCount.toString(),
                        fontSize = 12.sp,
                        color = FunkyTextSecondary
                    )
                }
            }
        }
    }
}
