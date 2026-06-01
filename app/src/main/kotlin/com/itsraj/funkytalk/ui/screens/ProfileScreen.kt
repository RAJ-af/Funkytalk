package com.itsraj.funkytalk.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

private val TABS = listOf("Moments", "About")

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val profile by authViewModel.userProfile.collectAsState()
    var tab by remember { mutableIntStateOf(0) }
    var showSettings by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val p = profile ?: return Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = FunkyYellow)
    }

    val name = p.profile_name ?: "Rohit"
    val atHandle = "@${p.username ?: "Rohit"}"

    Box(Modifier.fillMaxSize().background(FunkyBackground)) {
        Column(Modifier.fillMaxSize().verticalScroll(scrollState)) {
            // ═══════ TALL VIBRANT BANNER ══════════════════════════
            Box(Modifier.fillMaxWidth().height(220.dp)) {
                // Vibrant Gradient Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF1B6B), // Vibrant Pink
                                    Color(0xFF45CAFF), // Bright Blue
                                    Color(0xFFF6C945)  // Funky Yellow
                                )
                            )
                        )
                )

                // Top Actions (Username + Settings)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 6.dp)
                    ) {
                        Text(atHandle, style = AppTextStyle.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp))
                        Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy",
                            tint = Color.White, modifier = Modifier.size(18.dp))
                    }

                    IconButton(
                        onClick = { showSettings = true },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f))
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(26.dp))
                    }
                }
            }

            // ═══════ AVATAR + STATS (OVERLAP) ════════════════════
            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().offset(y = (-55).dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Avatar with thick yellow border
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = CircleShape,
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(4.dp, FunkyYellow),
                        shadowElevation = 12.dp
                    ) {
                        AsyncImage(
                            model = p.avatar_url ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                            contentDescription = name,
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Stats row
                    Row(
                        modifier = Modifier.padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        StatItem("512", "Followers")
                        StatItem("320", "Following")
                    }
                }

                // Name and Bio
                Column(Modifier.offset(y = (-40).dp)) {
                    Text(name, style = AppTextStyle.displayMedium.copy(color = FunkyTextPrimary, fontSize = 32.sp, fontWeight = FontWeight.Bold))
                    Text(p.bio ?: "Travel | Photography | Good vibes only 📷",
                        style = AppTextStyle.bodyMedium.copy(color = FunkyTextSecondary, fontWeight = FontWeight.Medium, fontSize = 16.sp))

                    Spacer(Modifier.height(20.dp))

                    // Unified Gender & Age Badge
                    UnifiedBadge(gender = p.gender ?: "Male", age = p.age ?: 23)
                }
            }

            // ═══════ TABS ════════════════════════════════════════
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(68.dp),
                shape = RoundedCornerShape(28.dp),
                color = FunkySurfaceElevated
            ) {
                Row(Modifier.fillMaxSize().padding(6.dp)) {
                    TABS.forEachIndexed { i, label ->
                        val sel = i == tab
                        Box(
                            Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(22.dp))
                                .background(if (sel) FunkyYellow else Color.Transparent)
                                .clickable(remember { MutableInteractionSource() }, null) { tab = i },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(label, style = AppTextStyle.label.copy(
                                color = if (sel) FunkyTextOnYellow else FunkyTextSecondary,
                                fontWeight = if (sel) FontWeight.Bold else FontWeight.SemiBold,
                                fontSize = 16.sp
                            ))
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ═══════ MOMENTS FEED ═════════════════════════════════
            Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                MomentCard(
                    author = name,
                    avatarUrl = p.avatar_url,
                    time = "2 hours ago",
                    content = "Exploring the hidden gems of the city today. The architecture here is breathtaking! 🏛️✨",
                    images = listOf(
                        "https://images.unsplash.com/photo-1520310809185-5cc119cf8b08?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                        "https://images.unsplash.com/photo-1778461456551-2126d8a7fa67?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400"
                    )
                )
            }

            Spacer(Modifier.height(120.dp))
        }

        // Settings dropdown
        Box(Modifier.fillMaxWidth().statusBarsPadding().padding(top = 8.dp, end = 16.dp), contentAlignment = Alignment.TopEnd) {
            DropdownMenu(
                expanded = showSettings,
                onDismissRequest = { showSettings = false },
                modifier = Modifier.background(FunkySurface, AppShapes.lg).width(180.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Settings", style = AppTextStyle.label) },
                    leadingIcon = { Icon(Icons.Outlined.Settings, null, Modifier.size(20.dp)) },
                    onClick = { showSettings = false }
                )
                DropdownMenuItem(
                    text = { Text("Log out", style = AppTextStyle.label.copy(color = FunkyError)) },
                    leadingIcon = { Icon(Icons.Outlined.Logout, null, Modifier.size(20.dp), tint = FunkyError) },
                    onClick = { showSettings = false; authViewModel.logout() }
                )
            }
        }
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, style = AppTextStyle.titleLarge.copy(color = FunkyTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold))
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontSize = 14.sp))
    }
}

@Composable
fun UnifiedBadge(gender: String, age: Int) {
    Surface(
        color = FunkyYellowSoft,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val icon = if (gender.lowercase() == "female") "♀" else "♂"
            Text(icon, fontSize = 18.sp, color = FunkyTextOnYellow, fontWeight = FontWeight.Bold)
            Text(gender, style = AppTextStyle.label.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Bold, fontSize = 15.sp))
            Box(Modifier.size(4.dp).clip(CircleShape).background(FunkyTextOnYellow.copy(alpha = 0.4f)))
            Text("$age Years", style = AppTextStyle.label.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Bold, fontSize = 15.sp))
        }
    }
}

@Composable
fun MomentCard(author: String, avatarUrl: String?, time: String, content: String, images: List<String>) {
    Surface(
        modifier = Modifier.fillMaxWidth().shadow(6.dp, RoundedCornerShape(36.dp)),
        color = FunkySurface,
        shape = RoundedCornerShape(36.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    AsyncImage(
                        model = avatarUrl ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(author, style = AppTextStyle.titleMedium.copy(color = FunkyTextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp))
                        Text(time, style = AppTextStyle.caption.copy(color = FunkyTextSecondary, fontSize = 12.sp))
                    }
                }
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = FunkyTextTertiary)
            }

            Spacer(Modifier.height(18.dp))
            Text(content, style = AppTextStyle.bodyMedium.copy(color = FunkyTextPrimary, lineHeight = 22.sp, fontSize = 15.sp))
            Spacer(Modifier.height(18.dp))

            if (images.size >= 2) {
                Row(Modifier.fillMaxWidth().height(220.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(
                        model = images[0],
                        contentDescription = null,
                        modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            HorizontalDivider(color = FunkyBorderLight, thickness = 1.2.dp)
            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(26.dp), tint = FunkyTextSecondary)
                    Text("124", style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 14.sp))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Outlined.ChatBubbleOutline, null, Modifier.size(24.dp), tint = FunkyTextSecondary)
                    Text("48", style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 14.sp))
                }
                Icon(Icons.Outlined.Share, null, Modifier.size(26.dp), tint = FunkyTextSecondary)
            }
        }
    }
}
