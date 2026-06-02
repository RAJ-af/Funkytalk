package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.*

@Composable
fun FunkyMomentCard(
    author: String,
    avatarUrl: String?,
    time: String,
    content: String,
    images: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = FunkySurfaceElevated,
        shape = AppShapes.card,
        shadowElevation = 0.dp
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AsyncImage(
                    model = avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
                    contentDescription = null,
                    modifier = Modifier.size(32.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.weight(1f)) {
                    Text(author, style = AppTextStyle.label.copy(fontWeight = FontWeight.SemiBold, fontSize = 13.sp))
                    Text(time, style = AppTextStyle.caption.copy(color = FunkyTextSecondary, fontSize = 10.sp))
                }
                Icon(Icons.Outlined.MoreVert, null, tint = FunkyTextTertiary, modifier = Modifier.size(18.dp))
            }

            Spacer(Modifier.height(10.dp))
            Text(content, style = AppTextStyle.body.copy(fontSize = 13.sp, lineHeight = 18.sp))

            if (images.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth().height(120.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    images.forEach { img ->
                        AsyncImage(
                            model = img,
                            contentDescription = null,
                            modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(16.dp), tint = FunkyTextSecondary)
                    Text("124", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Outlined.ChatBubbleOutline, null, Modifier.size(16.dp), tint = FunkyTextSecondary)
                    Text("48", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                }
                Icon(Icons.Outlined.Share, null, Modifier.size(16.dp), tint = FunkyTextSecondary)
            }
        }
    }
}
