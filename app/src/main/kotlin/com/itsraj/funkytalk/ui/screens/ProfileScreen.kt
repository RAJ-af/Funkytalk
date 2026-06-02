package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.components.FunkySegmentedTabs
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val userProfile by authViewModel.userProfile.collectAsState()
    val scrollState = rememberScrollState()
    var showSettings by remember { mutableStateOf(false) }

    val bannerUrl = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?w=1000"
    val avatarUrl = userProfile?.avatar_url ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400"

    Box(Modifier.fillMaxSize().background(FunkyBackground)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header Section (Banner + Avatar Overlay)
            Box(Modifier.fillMaxWidth().height(260.dp)) {
                // Banner
                AsyncImage(
                    model = bannerUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // Top-Left @Username (Inside Banner)
                Text(
                    text = "@${userProfile?.username ?: "username"}",
                    style = AppTextStyle.label.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        shadow = androidx.compose.ui.graphics.Shadow(Color.Black.copy(alpha = 0.5f), blurRadius = 4f)
                    ),
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )

                // Top-Right Settings Icon (Inside Banner)
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                ) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Overlapping Avatar (Bottom-Left)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                ) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(4.dp, FunkyBackground, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    // Country Flag Badge (Bottom-Right of Avatar)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-4).dp, y = (-4).dp)
                            .size(28.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            getFlagEmoji(userProfile?.country ?: "IN"),
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Stats Centered Below Banner
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem("2.4k", "Followers")
                Spacer(Modifier.width(40.dp))
                StatItem("842", "Following")
            }

            // User Info Section
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Display Name Below Avatar Area
                Text(
                    userProfile?.profile_name ?: "Display Name",
                    style = AppTextStyle.headline.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(Modifier.height(4.dp))

                // Bio Below Display Name
                Text(
                    userProfile?.bio ?: "No bio yet. Tap settings to edit your profile.",
                    style = AppTextStyle.body.copy(color = FunkyTextSecondary, lineHeight = 20.sp)
                )

                Spacer(Modifier.height(12.dp))

                // Gender-Age Pill
                UnifiedBadge(userProfile?.gender ?: "Unknown", userProfile?.age ?: 0)
            }

            Spacer(Modifier.height(24.dp))

            // Segmented Tabs
            var selectedTab by remember { mutableStateOf(0) }
            FunkySegmentedTabs(
                tabs = listOf("Feed", "About"),
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Tab Content
            if (selectedTab == 0) {
                Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    MomentCard(
                        author = userProfile?.profile_name ?: "Me",
                        avatarUrl = avatarUrl,
                        time = "2 hours ago",
                        content = "Enjoying the sunset! #lifestyle #vibes",
                        images = listOf(
                            "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=800",
                            "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=800"
                        )
                    )
                    Spacer(Modifier.height(80.dp)) // Bottom Nav Padding
                }
            } else {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    AboutSection("Native Language", (userProfile?.native_languages ?: listOf("English")).joinToString(", "))
                    AboutSection("Learning", (userProfile?.learning_languages ?: listOf("Spanish", "French")).joinToString(", "))
                    AboutSection("Hobbies", (userProfile?.hobbies ?: listOf("Music", "Travel", "Photography")).joinToString(", "))
                    Spacer(Modifier.height(80.dp))
                }
            }
        }

        // Settings Bottom Sheet
        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                containerColor = FunkySurface,
                dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
            ) {
                SettingsContent(
                    navController = navController,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(0)
                        }
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
        SettingsItem(Icons.Outlined.Block, "Blocked Users", onClick = { onDismiss(); navController.navigate(Screen.BlockedUsers.route) })

        SettingsItem(
            Icons.Outlined.PrivacyTip,
            "Privacy Policy",
            onClick = {
                onDismiss()
                navController.navigate(Screen.WebView.createRoute("Privacy Policy", "https://lost39.github.io/funkytalk/#"))
            }
        )
        SettingsItem(
            Icons.Outlined.Description,
            "Terms of Service",
            onClick = {
                onDismiss()
                navController.navigate(Screen.WebView.createRoute("Terms of Service", "https://lost39.github.io/funkytalk/#"))
            }
        )

        SettingsItem(Icons.Outlined.Info, "About FunkyTalk", onClick = { onDismiss(); navController.navigate(Screen.About.route) })
        SettingsItem(Icons.Outlined.Feedback, "Feedback", onClick = { onDismiss(); navController.navigate(Screen.Feedback.route) })

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
                        model = avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
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
                    Icons.Outlined.MoreVert,
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
