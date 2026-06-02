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
            CenterAlignedTopAppBar(
                title = { Text("Blocked Users", style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.SemiBold)) },
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
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp, vertical = 16.dp)) {
            Surface(
                color = FunkySurfaceElevated,
                shape = AppShapes.card,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    items(blockedUsers) { user ->
                        BlockedUserRow(user)
                        if (user != blockedUsers.last()) {
                            HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(horizontal = 16.dp))
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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = user.avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200",
                contentDescription = null,
                modifier = Modifier.size(44.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                getFlagEmoji(user.countryCode),
                modifier = Modifier.align(Alignment.BottomEnd).offset(x = 4.dp, y = 4.dp).size(16.dp).background(Color.White, CircleShape).padding(1.dp),
                fontSize = 10.sp
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(user.name, style = AppTextStyle.label.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp))
            Text("@${user.username}", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
        }

        Button(
            onClick = { /* Unblock */ },
            colors = ButtonDefaults.buttonColors(containerColor = FunkyBackground, contentColor = FunkyTextPrimary),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier.height(30.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            border = BorderStroke(1.dp, FunkyBorder)
        ) {
            Text("Unblock", style = AppTextStyle.labelSmall.copy(fontSize = 11.sp))
        }
    }
}
