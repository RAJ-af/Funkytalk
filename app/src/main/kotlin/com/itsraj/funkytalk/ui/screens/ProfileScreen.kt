package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.Message
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
import com.itsraj.funkytalk.ui.components.ProfileTabs
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val userProfile = authViewModel.userProfile.collectAsState().value
    val username = userProfile?.profile_name ?: "Rajdeep Singh"
    val bio = userProfile?.bio ?: "Building something cool with Gen-Z vibes."
    val avatarUrl = userProfile?.avatar_url ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400"
    val bannerUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=1080"

    Scaffold(
        containerColor = FunkyBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ─── Header Section (Banner + Avatar) ─────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                // Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(FunkyYellow.copy(alpha = 0.8f), FunkyYellow.copy(alpha = 0.4f))
                            )
                        )
                ) {
                    AsyncImage(
                        model = bannerUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Avatar + Flag Overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 24.dp)
                        .size(110.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(96.dp),
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(4.dp, FunkyBackground)
                    ) {
                        AsyncImage(
                            model = avatarUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // Flag Badge
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 14.dp)
                            .size(32.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(getFlagEmoji(userProfile?.country ?: "IN"), fontSize = 18.sp)
                        }
                    }
                }

                // Settings Icon (Top Right)
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Outlined.Settings, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }

            // ─── User Info Section ──────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = username,
                    style = AppTextStyle.displaySmall.copy(
                        color = FunkyTextPrimary,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    )
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = bio,
                    style = AppTextStyle.body.copy(
                        color = FunkyTextSecondary,
                        lineHeight = 20.sp,
                        fontSize = 14.sp
                    )
                )

                Spacer(Modifier.height(16.dp))

                UnifiedBadge(gender = userProfile?.gender ?: "Male", age = userProfile?.age ?: 22)

                Spacer(Modifier.height(24.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    StatItem("1.2k", "Followers")
                    StatItem("482", "Following")
                    StatItem("15k", "Hearts")
                }
            }

            Spacer(Modifier.height(28.dp))

            // ─── Content Tabs ──────────────────────────────────
            Column(Modifier.padding(horizontal = 24.dp)) {
                ProfileTabs(
                    tabs = listOf("Moments", "About", "Badges"),
                    selectedIndex = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = Modifier.height(52.dp)
                )

                Spacer(Modifier.height(20.dp))

                // Tab Content
                when (selectedTab) {
                    0 -> {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            MomentCard(
                                author = username,
                                avatarUrl = avatarUrl,
                                time = "2h ago",
                                content = "Just had the best coffee in town! ☕✨ #lifestyle",
                                images = listOf(
                                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=800",
                                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=800"
                                )
                            )
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(FunkySurfaceElevated, RoundedCornerShape(24.dp))
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AboutSection("Languages", "Native in Hindi, English. Learning French.")
                            AboutSection("Interests", "Coding, Music, Travel, Gaming")
                            AboutSection("Joined", "March 2024")
                        }
                    }
                    2 -> {
                        Box(Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                            Text("No badges yet.", color = FunkyTextTertiary)
                        }
                    }
                }
            }

            Spacer(Modifier.height(100.dp)) // Extra space for bottom nav
        }

        // ─── Settings Bottom Sheet ───────────────────────────
        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                sheetState = sheetState,
                containerColor = FunkyBackground,
                dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
            ) {
                SettingsContent(
                    navController = navController,
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
fun SettingsContent(navController: NavController, onLogout: () -> Unit, onDismiss: () -> Unit) {
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

        SettingsItem(Icons.Outlined.Edit, "Edit Profile", onClick = { onDismiss(); navController.navigate(Screen.EditProfile.route) })
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
                " Years",
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
