package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.components.*
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val tabs = listOf("Moments", "About")

    Scaffold(
        containerColor = FunkyBackground,
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Outlined.Settings, null, tint = FunkyTextPrimary, modifier = Modifier.size(20.dp))
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                ProfileHeaderSection()
            }

            item {
                Column(Modifier.padding(horizontal = 24.dp)) {
                    FunkySegmentedTabs(
                        tabs = tabs,
                        selectedIndex = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it },
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                }
            }

            if (selectedTabIndex == 0) {
                items(dummyMoments) { moment ->
                    Box(Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        MomentCard(
                            author = moment.author,
                            avatarUrl = moment.avatarUrl,
                            time = moment.time,
                            content = moment.content,
                            images = moment.images
                        )
                    }
                }
            } else {
                item {
                    AboutTabContent()
                }
            }
        }

        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                sheetState = sheetState,
                containerColor = Color.Transparent,
                dragHandle = null,
                scrimColor = Color.Black.copy(alpha = 0.32f)
            ) {
                SettingsSheetContent(
                    navController = navController,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Welcome.route) {
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
fun ProfileHeaderSection() {
    Box(Modifier.fillMaxWidth().height(320.dp)) {
        MeshGradient(Modifier.fillMaxWidth().height(200.dp))

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(110.dp),
                shape = CircleShape,
                border = BorderStroke(4.dp, Color.White),
                shadowElevation = 8.dp
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Raj Kumar",
                style = AppTextStyle.displayLarge.copy(fontSize = 26.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(getFlagEmoji("IN"), fontSize = 18.sp)
                UnifiedBadge(gender = "Male", age = 22)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("1.2k", "Followers")
                StatItem("842", "Following")
                StatItem("15k", "Moments")
            }
        }
    }
}

@Composable
fun AboutTabContent() {
    Column(
        Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        GroupedCard(title = "Bio") {
            Text(
                "Explorer 🌍 | Tech Enthusiast 💻 | Coffee Addict ☕️\nAlways looking for new friends to chat with!",
                style = AppTextStyle.body.copy(fontSize = 14.sp, lineHeight = 22.sp, color = FunkyTextPrimary)
            )
        }

        GroupedCard(title = "Languages") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LanguageBadge("English", "Fluent")
                LanguageBadge("Hindi", "Native")
            }
        }

        GroupedCard(title = "Interests") {
            val interests = listOf("Music", "Gaming", "Travel", "Coding", "Anime")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                interests.forEach { interest ->
                    Surface(
                        shape = RoundedCornerShape(100.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, FunkyBorderLight)
                    ) {
                        Text(
                            interest,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageBadge(lang: String, level: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, FunkyBorderLight)
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(lang, style = AppTextStyle.label.copy(fontWeight = FontWeight.SemiBold))
            Text(level, style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
        }
    }
}

@Composable
fun SettingsSheetContent(navController: NavController, onLogout: () -> Unit, onDismiss: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.9f),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp)
        ) {
            Box(
                Modifier
                    .size(40.dp, 4.dp)
                    .clip(CircleShape)
                    .background(FunkyBorder)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "Settings",
                style = AppTextStyle.headline.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(24.dp))

            GroupedCard {
                SettingsRow(Icons.Outlined.Edit, "Edit Profile") {
                    onDismiss()
                    navController.navigate(Screen.EditProfile.route)
                }
                SettingsDivider()
                SettingsRow(Icons.Outlined.Block, "Blocked Users") {
                    onDismiss()
                    navController.navigate(Screen.BlockedUsers.route)
                }
                SettingsDivider()
                SettingsRow(Icons.Outlined.Info, "About FunkyTalk") {
                    onDismiss()
                    navController.navigate(Screen.About.route)
                }
                SettingsDivider()
                SettingsRow(Icons.Outlined.Feedback, "Feedback") {
                    onDismiss()
                    navController.navigate(Screen.Feedback.route)
                }
                SettingsDivider()
                SettingsRow(Icons.Outlined.PrivacyTip, "Privacy Policy") {
                    onDismiss()
                    navController.navigate(Screen.WebView.createRoute("Privacy Policy", "https://lost39.github.io/funkytalk/#"))
                }
                SettingsDivider()
                SettingsRow(Icons.Outlined.Description, "Terms of Service") {
                    onDismiss()
                    navController.navigate(Screen.WebView.createRoute("Terms of Service", "https://lost39.github.io/funkytalk/#"))
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onDismiss(); onLogout() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyError.copy(alpha = 0.1f), contentColor = FunkyError),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("Log Out", style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingsRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(icon, null, Modifier.size(22.dp), tint = FunkyTextPrimary)
        Text(label, style = AppTextStyle.label.copy(fontSize = 15.sp, fontWeight = FontWeight.Medium))
        Spacer(Modifier.weight(1f))
        Icon(Icons.Outlined.ChevronRight, null, Modifier.size(18.dp), tint = FunkyTextTertiary)
    }
}

@Composable
fun SettingsDivider() {
    HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(start = 38.dp))
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Text(label, style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
    }
}

@Composable
fun UnifiedBadge(gender: String, age: Int) {
    Surface(
        color = FunkyYellowSoft,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val icon = if (gender.lowercase() == "female") "♀" else "♂"
            Text(icon, fontSize = 12.sp, color = FunkyTextOnYellow)
            Text("$age", style = AppTextStyle.labelSmall.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Bold))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MomentCard(author: String, avatarUrl: String?, time: String, content: String, images: List<String>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = FunkySurfaceElevated,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, FunkyBorderLight)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(
                    model = avatarUrl ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
                    contentDescription = null,
                    modifier = Modifier.size(36.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.weight(1f)) {
                    Text(author, style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold))
                    Text(time, style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                }
                Icon(Icons.Outlined.MoreVert, null, tint = FunkyTextTertiary, modifier = Modifier.size(20.dp))
            }

            Spacer(Modifier.height(12.dp))
            Text(content, style = AppTextStyle.body.copy(fontSize = 14.sp, lineHeight = 20.sp, color = FunkyTextPrimary))

            if (images.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth().height(160.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    images.forEach { img ->
                        AsyncImage(
                            model = img,
                            contentDescription = null,
                            modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
                    Text("124", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.AutoMirrored.Outlined.Message, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
                    Text("48", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                }
                Icon(Icons.Outlined.Share, null, Modifier.size(20.dp), tint = FunkyTextSecondary)
            }
        }
    }
}

data class Moment(val author: String, val avatarUrl: String?, val time: String, val content: String, val images: List<String>)

val dummyMoments = listOf(
    Moment("Raj Kumar", null, "2 hours ago", "Just had the best coffee in town! ☕️✨", listOf("https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800")),
    Moment("Raj Kumar", null, "Yesterday", "Coding all night for the new update. Stay tuned! 🚀💻", emptyList())
)

fun getFlagEmoji(countryCode: String): String {
    if (countryCode.length != 2) return "🇮🇳"
    val firstLetter = Character.codePointAt(countryCode.uppercase(), 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode.uppercase(), 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}
