package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockedUsersScreen(onBack: () -> Unit) {
    // Mock data
    val blockedUsers = listOf(
        BlockedUser("1", "Alex Rivera", "arivera", "US", null),
        BlockedUser("2", "Yuki Tanaka", "yuki_t", "JP", null),
        BlockedUser("3", "Sarah Smith", "ssmith", "GB", null)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Blocked Users",
                        style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = FunkyTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = FunkyBackground)
            )
        },
        containerColor = FunkyBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(blockedUsers) { user ->
                BlockedUserRow(user)
            }
        }
    }
}

data class BlockedUser(
    val id: String,
    val name: String,
    val username: String,
    val countryCode: String,
    val avatarUrl: String?
)

@Composable
fun BlockedUserRow(user: BlockedUser) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = user.avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200",
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            // Flag badge
            Text(
                getFlagEmoji(user.countryCode),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp)
                    .background(Color.White, CircleShape)
                    .padding(1.dp),
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(
                user.name,
                style = AppTextStyle.titleMedium.copy(color = FunkyTextPrimary)
            )
            Text(
                "@${user.username}",
                style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary)
            )
        }

        Button(
            onClick = { /* Unblock logic */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = FunkySurfaceElevated,
                contentColor = FunkyTextPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
            modifier = Modifier.height(36.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text("Unblock", style = AppTextStyle.labelSmall.copy(fontWeight = FontWeight.Medium))
        }
    }
}
