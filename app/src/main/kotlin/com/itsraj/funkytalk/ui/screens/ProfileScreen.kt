package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.data.model.UserProfile
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val profile by authViewModel.userProfile.collectAsState()
    var showSettings by remember { mutableStateOf(false) }
    var tab by remember { mutableIntStateOf(0) }
    val TABS = listOf("Moments", "About")
    val sheetState = rememberModalBottomSheetState()

    Box(
        Modifier
            .fillMaxSize()
            .background(FunkyBackground)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ═══════ HEADER / BANNER ═════════════════════════════
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                // Banner Gradient
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(FunkyYellow.copy(alpha = 0.8f), FunkyYellow.copy(alpha = 0.2f))
                            )
                        )
                )

                // Settings Icon (Top Right)
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 12.dp)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = FunkyTextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Profile Info Overlap
                val p = profile ?: UserProfile()
                val name = p.profile_name ?: p.username ?: "Unknown User"

                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 24.dp)
                        .offset(y = 48.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Side: Name and Country
                    Column(Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = name,
                            style = AppTextStyle.displaySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                letterSpacing = (-0.5).sp
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(getFlagEmoji(p.country ?: "IN"), fontSize = 16.sp)
                            Text(
                                "India",
                                style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontWeight = FontWeight.Medium)
                            )
                        }
                    }

                    // Right Side: Avatar with Badge
                    Box(contentAlignment = Alignment.BottomEnd) {
                        AsyncImage(
                            model = p.avatar_url ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                            contentDescription = null,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .border(4.dp, FunkyBackground, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        // Country flag badge overlap
                        Surface(
                            color = Color.White,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(28.dp)
                                .offset(x = (-2).dp, y = (-2).dp),
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(getFlagEmoji(p.country ?: "IN"), fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(64.dp))

            // ═══════ STATS ═══════════════════════════════════════
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    StatItem("2.4k", "Followers")
                    StatItem("186", "Following")
                    StatItem("12k", "Likes")
                }
            }

            Spacer(Modifier.height(24.dp))

            // ═══════ BIO / BADGE ═════════════════════════════════
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val p = profile ?: UserProfile()
                UnifiedBadge(gender = p.gender ?: "Male", age = p.age ?: 23)
                Text(
                    p.bio ?: "No bio yet. Tap to add one and tell the world about yourself!",
                    style = AppTextStyle.bodySmall.copy(
                        color = FunkyTextPrimary,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            Spacer(Modifier.height(24.dp))

            // ═══════ TABS ════════════════════════════════════════
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                color = FunkySurfaceElevated
            ) {
                Row(Modifier.fillMaxSize().padding(4.dp)) {
                    TABS.forEachIndexed { i, label ->
                        val sel = i == tab
                        Box(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(22.dp))
                                .background(if (sel) FunkyYellow else Color.Transparent)
                                .clickable(
                                    remember { MutableInteractionSource() },
                                    null
                                ) { tab = i },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                label,
                                style = AppTextStyle.label.copy(
                                    color = if (sel) FunkyTextOnYellow else FunkyTextSecondary,
                                    fontWeight = if (sel) FontWeight.Medium else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ═══════ TAB CONTENT ═════════════════════════════════
            Box(Modifier.padding(horizontal = 16.dp)) {
                val p = profile ?: UserProfile()
                if (tab == 0) {
                    // Moments Feed
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        MomentCard(
                            author = p.profile_name ?: p.username ?: "Unknown",
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

        // Settings Bottom Sheet
        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                sheetState = sheetState,
                containerColor = FunkySurface,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                dragHandle = {
                    Surface(
                        Modifier.padding(vertical = 12.dp),
                        color = FunkyBorder,
                        shape = CircleShape
                    ) {
                        Box(Modifier.size(width = 32.dp, height = 4.dp))
                    }
                }
            ) {
                SettingsContent(
                    onLogout = {
                        showSettings = false
                        authViewModel.logout()
                    },
                    onDismiss = { showSettings = false }
                )
            }
        }
    }
}

@Composable
fun SettingsContent(onLogout: () -> Unit, onDismiss: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            "Settings",
            style = AppTextStyle.headline.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        SettingsItem(Icons.Outlined.Edit, "Edit Profile", onClick = onDismiss)
        SettingsItem(Icons.Outlined.Block, "Blocked Users", onClick = onDismiss)
        SettingsItem(Icons.Outlined.PrivacyTip, "Privacy Policy", onClick = onDismiss)
        SettingsItem(Icons.Outlined.Info, "About FunkyTalk", onClick = onDismiss)
        SettingsItem(Icons.Outlined.Feedback, "Feedback", onClick = onDismiss)

        Spacer(Modifier.height(8.dp))
        HorizontalDivider(color = FunkyBorderLight)
        Spacer(Modifier.height(8.dp))

        SettingsItem(
            Icons.AutoMirrored.Outlined.Logout,
            "Logout",
            color = FunkyError,
            onClick = onLogout
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    color: Color = FunkyTextPrimary,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(icon, null, Modifier.size(22.dp), tint = color.copy(alpha = 0.8f))
        Text(
            label,
            style = AppTextStyle.label.copy(
                color = color,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )
        Spacer(Modifier.weight(1f))
        Icon(Icons.Outlined.ChevronRight, null, Modifier.size(18.dp), tint = FunkyTextTertiary)
    }
}

@Composable
fun AboutSection(title: String, content: String) {
    Column {
        Text(
            title,
            style = AppTextStyle.labelSmall.copy(
                color = FunkyTextSecondary,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        )
        Spacer(Modifier.height(4.dp))
        Text(content, style = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 14.sp))
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            count,
            style = AppTextStyle.titleMedium.copy(
                color = FunkyTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            label,
            style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontSize = 12.sp)
        )
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
            Text(icon, fontSize = 14.sp, color = FunkyTextOnYellow, fontWeight = FontWeight.SemiBold)
            Text(
                gender,
                style = AppTextStyle.label.copy(
                    color = FunkyTextOnYellow,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            )
            Box(Modifier.size(3.dp).clip(CircleShape).background(FunkyTextOnYellow.copy(alpha = 0.4f)))
            Text(
                "$age Years",
                style = AppTextStyle.label.copy(
                    color = FunkyTextOnYellow,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Composable
fun MomentCard(
    author: String,
    avatarUrl: String?,
    time: String,
    content: String,
    images: List<String>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = FunkySurface,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            author,
                            style = AppTextStyle.label.copy(
                                color = FunkyTextPrimary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            time,
                            style = AppTextStyle.caption.copy(color = FunkyTextSecondary, fontSize = 10.sp)
                        )
                    }
                }
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = FunkyTextTertiary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.height(12.dp))
            Text(
                content,
                style = AppTextStyle.body.copy(
                    color = FunkyTextPrimary,
                    lineHeight = 18.sp,
                    fontSize = 13.sp
                )
            )
            Spacer(Modifier.height(12.dp))

            if (images.size >= 2) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    AsyncImage(
                        model = images[0],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = FunkyBorderLight.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        null,
                        Modifier.size(18.dp),
                        tint = FunkyTextSecondary
                    )
                    Text(
                        "124",
                        style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 12.sp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.ChatBubbleOutline,
                        null,
                        Modifier.size(16.dp),
                        tint = FunkyTextSecondary
                    )
                    Text(
                        "48",
                        style = AppTextStyle.label.copy(color = FunkyTextSecondary, fontSize = 12.sp)
                    )
                }
                Icon(
                    Icons.Outlined.Share,
                    null,
                    Modifier.size(18.dp),
                    tint = FunkyTextSecondary
                )
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
