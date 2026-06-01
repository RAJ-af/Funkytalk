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
import com.itsraj.funkytalk.data.model.allCountries
import com.itsraj.funkytalk.ui.components.CircularFlag
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

    val name = p.profile_name ?: "User"
    val atHandle = "@${p.username ?: "user"}"
    val country = allCountries.find { it.code == p.country }

    Box(Modifier.fillMaxSize().background(FunkyBackground)) {
        Column(Modifier.fillMaxSize().verticalScroll(scrollState)) {
            // ═══════ BANNER ══════════════════════════════════════
            Box(Modifier.fillMaxWidth().height(Sizes.coverHeight + 40.dp)) {
                // Banner Image / Gradient
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?q=80&w=1000&auto=format&fit=crop",
                    contentDescription = "Banner",
                    modifier = Modifier.fillMaxWidth().height(Sizes.coverHeight).clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                    contentScale = ContentScale.Crop
                )

                // Dark overlay for top bar items
                Box(Modifier.fillMaxWidth().height(60.dp).background(
                    Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent))
                ))

                // Top Actions
                Row(
                    Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = Spacing.lg, vertical = Spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                        Text(atHandle, style = AppTextStyle.titleMedium.copy(color = Color.White))
                        Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy",
                            tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(14.dp))
                    }

                    IconButton(
                        onClick = { showSettings = true },
                        modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }

            // ═══════ AVATAR + STATS ════════════════════════════════
            Column(Modifier.fillMaxWidth().padding(horizontal = Spacing.xl).offset(y = (-50).dp)) {
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    // Avatar with Yellow Ring
                    Box(Modifier.size(Sizes.avatarHero + 8.dp)) {
                        Surface(
                            Modifier.size(Sizes.avatarHero).align(Alignment.Center),
                            shape = CircleShape,
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(3.dp, FunkyYellow)
                        ) {
                            AsyncImage(
                                model = p.avatar_url ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                                contentDescription = name,
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                        country?.let { ce ->
                            Surface(
                                Modifier.align(Alignment.BottomEnd).size(26.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 4.dp
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    CircularFlag(code = ce.code, size = 20.dp)
                                }
                            }
                        }
                    }

                    // Stats
                    Row(Modifier.weight(1f).padding(bottom = Spacing.sm), horizontalArrangement = Arrangement.spacedBy(Spacing.xxl)) {
                        StatItem("512", "Followers")
                        StatItem("320", "Following")
                    }
                }

                Spacer(Modifier.height(Spacing.md))

                // Name and Bio
                Text(name, style = AppTextStyle.displaySmall.copy(color = FunkyTextPrimary))
                Text(p.bio ?: "Travel | Photography | Good vibes only 📷", style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary))

                Spacer(Modifier.height(Spacing.md))

                // Tags
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    TagPill(text = "${p.gender ?: "Male"}", icon = if (p.gender == "Female") "♀" else "♂")
                    TagPill(text = "${p.age ?: 24} Years")
                }
            }

            // ═══════ TABS ════════════════════════════════════════
            Box(Modifier.fillMaxWidth().padding(horizontal = Spacing.xl).height(48.dp)
                .clip(AppShapes.xxl).background(FunkySurfaceElevated).padding(4.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    TABS.forEachIndexed { i, label ->
                        val sel = i == tab
                        Box(
                            Modifier.weight(1f).fillMaxHeight().clip(AppShapes.xl)
                                .background(if (sel) FunkyYellow else Color.Transparent)
                                .clickable(remember { MutableInteractionSource() }, null) { tab = i },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(label, style = AppTextStyle.label.copy(
                                color = if (sel) FunkyTextOnYellow else FunkyTextSecondary,
                                fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium
                            ))
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            // ═══════ MOMENTS FEED ═════════════════════════════════
            Column(Modifier.padding(horizontal = Spacing.xl), verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
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
                modifier = Modifier.background(FunkySurface, AppShapes.lg).width(160.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Settings", style = AppTextStyle.label) },
                    leadingIcon = { Icon(Icons.Outlined.Settings, null, Modifier.size(18.dp)) },
                    onClick = { showSettings = false }
                )
                DropdownMenuItem(
                    text = { Text("Log out", style = AppTextStyle.label.copy(color = FunkyError)) },
                    leadingIcon = { Icon(Icons.Outlined.Logout, null, Modifier.size(18.dp), tint = FunkyError) },
                    onClick = { showSettings = false; authViewModel.logout() }
                )
            }
        }
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, style = AppTextStyle.titleLarge.copy(color = FunkyTextPrimary))
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
    }
}

@Composable
fun TagPill(text: String, icon: String? = null) {
    Surface(
        color = FunkyYellowSoft,
        shape = AppShapes.pill,
        modifier = Modifier.height(28.dp)
    ) {
        Row(
            Modifier.padding(horizontal = Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icon?.let { Text(it, fontSize = 12.sp, color = FunkyTextOnYellow) }
            Text(text, style = AppTextStyle.labelSmall.copy(color = FunkyTextOnYellow))
        }
    }
}

@Composable
fun MomentCard(author: String, avatarUrl: String?, time: String, content: String, images: List<String>) {
    Surface(
        modifier = Modifier.fillMaxWidth().shadow(Elevation.card, AppShapes.xxl),
        color = FunkySurface,
        shape = AppShapes.xxl
    ) {
        Column(Modifier.padding(Spacing.lg)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    AsyncImage(
                        model = avatarUrl ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                        contentDescription = null,
                        modifier = Modifier.size(36.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(author, style = AppTextStyle.titleSmall.copy(color = FunkyTextPrimary))
                        Text(time, style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                    }
                }
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = FunkyTextTertiary)
            }

            Spacer(Modifier.height(Spacing.md))
            Text(content, style = AppTextStyle.bodySmall.copy(color = FunkyTextPrimary))
            Spacer(Modifier.height(Spacing.md))

            if (images.isNotEmpty()) {
                Row(Modifier.fillMaxWidth().height(160.dp), horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    images.forEach { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = null,
                            modifier = Modifier.weight(1f).fillMaxHeight().clip(AppShapes.lg),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = FunkyBorderLight, thickness = 1.dp)
            Spacer(Modifier.height(Spacing.sm))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
                    Text("124", style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    Icon(Icons.Outlined.ChatBubbleOutline, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
                    Text("48", style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
                }
                Icon(Icons.Outlined.Share, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
            }
        }
    }
}

private object Elevation {
    val card = 4.dp
}
