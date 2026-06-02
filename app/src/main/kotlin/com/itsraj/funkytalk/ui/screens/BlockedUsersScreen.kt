package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.BorderStroke
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
import com.itsraj.funkytalk.ui.components.*
import com.itsraj.funkytalk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockedUsersScreen(onBack: () -> Unit) {
    val blockedUsers = listOf(
        BlockedUser("1", "Alex Rivera", "arivera", "US", null),
        BlockedUser("2", "Yuki Tanaka", "yuki_t", "JP", null),
        BlockedUser("3", "Sarah Smith", "ssmith", "GB", null)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Blocked Users", style = AppTextStyle.headline.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = FunkyTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FunkyBackground)
            )
        },
        containerColor = FunkyBackground
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text(
                "You won't see messages or profiles from people you've blocked.",
                style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            GroupedCard {
                if (blockedUsers.isEmpty()) {
                    Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text("No blocked users", style = AppTextStyle.body.copy(color = FunkyTextTertiary))
                    }
                } else {
                    blockedUsers.forEachIndexed { index, user ->
                        BlockedUserRow(user)
                        if (index < blockedUsers.size - 1) {
                            HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(vertical = 12.dp))
                        }
                    }
                }
            }
        }
    }
}

data class BlockedUser(val id: String, val name: String, val username: String, val countryCode: String, val avatarUrl: String?)

@Composable
fun BlockedUserRow(user: BlockedUser) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200",
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(user.name, style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp))
            Text("@${user.username}", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
        }

        TextButton(
            onClick = { /* Unblock */ },
            colors = ButtonDefaults.textButtonColors(contentColor = FunkyError)
        ) {
            Text("Unblock", style = AppTextStyle.label.copy(fontWeight = FontWeight.SemiBold))
        }
    }
}
