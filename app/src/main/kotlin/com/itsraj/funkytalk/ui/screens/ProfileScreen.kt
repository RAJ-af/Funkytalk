package com.itsraj.funkytalk.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
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
            // ═══════ VIBRANT BANNER ══════════════════════════
            Box(Modifier.fillMaxWidth().height(180.dp)) {
                // Vibrant Gradient Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
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
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(atHandle, style = AppTextStyle.label.copy(color = Color.White, fontWeight = FontWeight.Medium, fontSize = 16.sp))
                        Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy",
                            tint = Color.White, modifier = Modifier.size(16.dp))
                    }

                    IconButton(
                        onClick = { showSettings = true },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }
            }

            // ═══════ AVATAR + STATS (OVERLAP) ════════════════════
            Column(Modifier.padding(horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .offset(y = (-40).dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Stats row (Left)
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        StatItem("512", "Followers")
                        StatItem("320", "Following")
                    }

                    // Avatar with Flag overlap (Right)
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Surface(
                            modifier = Modifier.size(96.dp),
                            shape = CircleShape,
                            color = Color.White,
                            border = BorderStroke(3.dp, FunkyYellow)
                        ) {
                            AsyncImage(
                                model = p.avatar_url ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                                contentDescription = name,
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Circular Flag Badge (Bottom Right)
                        Surface(
                            modifier = Modifier.size(30.dp).offset(x = 2.dp, y = 2.dp),
                            shape = CircleShape,
                            color = Color.White,
                            border = BorderStroke(1.5.dp, Color.White)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = getFlagEmoji(p.country ?: "IN"),
                                    style = TextStyle(fontSize = 15.sp)
                                )
                            }
                        }
                    }
                }

                // Name and Bio
                Column(Modifier.offset(y = (-24).dp)) {
                    Text(name, style = AppTextStyle.titleLarge.copy(color = FunkyTextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Medium))
                    Spacer(Modifier.height(4.dp))
                    Text(p.bio ?: "Travel | Photography | Good vibes only 📷",
                        style = AppTextStyle.body.copy(color = FunkyTextSecondary, fontWeight = FontWeight.Normal, fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(16.dp))

                    // Unified Gender & Age Badge
                    UnifiedBadge(gender = p.gender ?: "Male", age = p.age ?: 23)
                }
            }

            // ═══════ TABS ════════════════════════════════════════
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(52.dp),
                shape = RoundedCornerShape(26.dp),
                color = FunkySurfaceElevated
            ) {
                Row(Modifier.fillMaxSize().padding(4.dp)) {
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
                                fontWeight = if (sel) FontWeight.Medium else FontWeight.Normal,
                                fontSize = 14.sp
                            ))
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ═══════ TAB CONTENT ═════════════════════════════════
            Box(Modifier.padding(horizontal = 16.dp)) {
                if (tab == 0) {
                    // Moments Feed
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                } else {
                    // About Tab
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(FunkySurface, RoundedCornerShape(20.dp))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AboutSection("Hobbies", p.hobbies?.joinToString(" • ") ?: "Photography • Music • Coding")
                        AboutSection("Native Languages", p.native_languages?.joinToString(" • ") ?: "Hindi • English")
                        AboutSection("Learning", p.learning_languages?.joinToString(" • ") ?: "Spanish • Japanese")
                    }
                }
            }

            Spacer(Modifier.height(100.dp))
        }

        // Settings dropdown
        Box(Modifier.fillMaxWidth().statusBarsPadding().padding(top = 8.dp, end = 16.dp), contentAlignment = Alignment.TopEnd) {
            DropdownMenu(
                expanded = showSettings,
                onDismissRequest = { showSettings = false },
                modifier = Modifier.background(FunkySurface, AppShapes.lg).width(160.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Settings", style = AppTextStyle.label.copy(fontSize = 14.sp)) },
                    leadingIcon = { Icon(Icons.Outlined.Settings, null, Modifier.size(18.dp)) },
                    onClick = { showSettings = false }
                )
                DropdownMenuItem(
                    text = { Text("Log out", style = AppTextStyle.label.copy(color = FunkyError, fontSize = 14.sp)) },
                    leadingIcon = { Icon(Icons.Outlined.Logout, null, Modifier.size(18.dp), tint = FunkyError) },
                    onClick = { showSettings = false; authViewModel.logout() }
                )
            }
        }
    }
}

@Composable
fun AboutSection(title: String, content: String) {
    Column {
        Text(title, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontWeight = FontWeight.Medium, fontSize = 12.sp))
        Spacer(Modifier.height(2.dp))
        Text(content, style = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 14.sp))
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, style = AppTextStyle.titleMedium.copy(color = FunkyTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Medium))
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontSize = 12.sp))
    }
}

@Composable
fun UnifiedBadge(gender: String, age: Int) {
    Surface(
        color = FunkyYellowSoft,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val icon = if (gender.lowercase() == "female") "♀" else "♂"
            Text(icon, fontSize = 14.sp, color = FunkyTextOnYellow, fontWeight = FontWeight.Medium)
            Text(gender, style = AppTextStyle.label.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Medium, fontSize = 13.sp))
            Box(Modifier.size(3.dp).clip(CircleShape).background(FunkyTextOnYellow.copy(alpha = 0.4f)))
            Text("$age Years", style = AppTextStyle.label.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Medium, fontSize = 13.sp))
        }
    }
}

@Composable
fun MomentCard(author: String, avatarUrl: String?, time: String, content: String, images: List<String>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = FunkySurface,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AsyncImage(
                        model = avatarUrl ?: "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                        contentDescription = null,
                        modifier = Modifier.size(36.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(author, style = AppTextStyle.label.copy(color = FunkyTextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp))
                        Text(time, style = AppTextStyle.caption.copy(color = FunkyTextSecondary, fontSize = 10.sp))
                    }
                }
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = FunkyTextTertiary, modifier = Modifier.size(18.dp))
            }

            Spacer(Modifier.height(12.dp))
            Text(content, style = AppTextStyle.body.copy(color = FunkyTextPrimary, lineHeight = 18.sp, fontSize = 13.sp))
            Spacer(Modifier.height(12.dp))

            if (images.size >= 2) {
                Row(Modifier.fillMaxWidth().height(160.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AsyncImage(
                        model = images[0],
                        contentDescription = null,
                        modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = FunkyBorderLight.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(18.dp), tint = FunkyTextSecondary)
                    Text("124", style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 12.sp))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Outlined.ChatBubbleOutline, null, Modifier.size(16.dp), tint = FunkyTextSecondary)
                    Text("48", style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 12.sp))
                }
                Icon(Icons.Outlined.Share, null, Modifier.size(18.dp), tint = FunkyTextSecondary)
            }
        }
    }
}

fun getFlagEmoji(countryCode: String): String {
    if (countryCode.length != 2) return "🇮🇳"
    val firstLetter = Character.codePointAt(countryCode.uppercase(), 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode.uppercase(), 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}
