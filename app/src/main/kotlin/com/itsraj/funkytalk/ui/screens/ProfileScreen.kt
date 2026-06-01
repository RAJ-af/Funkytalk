package com.itsraj.funkytalk.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.data.model.allCountries
import com.itsraj.funkytalk.ui.components.CircularFlag
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthState
import com.itsraj.funkytalk.viewmodel.AuthViewModel

private val N950 = Color(0xFF0A0A0A)
private val N900 = Color(0xFF1A1A1A)
private val N500 = Color(0xFF888888)
private val N200 = Color(0xFFE5E5E5)
private val N100 = Color(0xFFF5F5F5)
private val N50  = Color(0xFFFAFAFA)
private val TABS = listOf("Feed", "About")

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val profile by authViewModel.userProfile.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    var tab by remember { mutableIntStateOf(0) }
    var showSettings by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated)
            navController.navigate(Screen.Welcome.route) { popUpTo(0) { inclusive = true } }
    }

    if (profile == null && authState !is AuthState.Error) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = N900) }
        return
    }
    if (authState is AuthState.Error && profile == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Could not load profile", color = N500)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = N900)) { Text("Retry", color = N50) }
            }
        }
        return
    }

    val p = profile!!
    val country = p.country?.let { cn -> allCountries.find { it.name == cn } }
    val name = p.profile_name ?: p.username ?: "User"
    val atHandle = "@" + (p.username ?: "user")

    Box(Modifier.fillMaxSize().background(Color.White)) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            // ═══════ STATUS BAR SPACER + BANNER (150dp) ════════════
            Spacer(Modifier.statusBarsPadding())
            Box(Modifier.fillMaxWidth().height(150.dp)) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1506116368634-53a1fdb60fbe?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                    contentDescription = "Cover", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )
                Box(Modifier.fillMaxSize().background(N950.copy(alpha = 0.10f)))
                // @username + copy icon
                Row(Modifier.align(Alignment.TopStart).padding(start = 24.dp, top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(atHandle, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.SansSerif,
                        color = Color.White, letterSpacing = 0.sp)
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy",
                        tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                }
                // Settings gear icon
                Box(Modifier.align(Alignment.TopEnd).padding(top = 12.dp, end = 16.dp).size(36.dp)
                    .clip(CircleShape).background(N950.copy(alpha = 0.30f)).clickable { showSettings = true },
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(20.dp)) }
            }

            // ═══════ AVATAR + STATS (overlaps banner) ═════════════
            Row(Modifier.fillMaxWidth().offset(y = (-48).dp).padding(horizontal = 24.dp),
                verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Box(Modifier.size(88.dp)) {
                    Surface(Modifier.size(80.dp).align(Alignment.Center), shape = CircleShape, color = Color.Transparent) {
                        Box(Modifier.fillMaxSize().clip(CircleShape)) {
                            AsyncImage(model = "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                                contentDescription = name, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        }
                    }
                    Box(Modifier.matchParentSize().clip(CircleShape).background(Color.Transparent))
                    country?.let { ce ->
                        Box(Modifier.align(Alignment.BottomEnd).size(22.dp).clip(CircleShape).background(Color.White),
                            contentAlignment = Alignment.Center
                        ) { CircularFlag(code = ce.code, size = 18.dp) }
                    }
                }
                Row(Modifier.padding(bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("512", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp, color = N950)
                        Text("Followers", fontSize = 12.sp, lineHeight = 16.sp, color = N500)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("320", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp, color = N950)
                        Text("Following", fontSize = 12.sp, lineHeight = 16.sp, color = N500)
                    }
                }
            }

            // ═══════ NAME + BIO (below avatar) ═════════════════════
            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp, lineHeight = 28.sp, color = N950,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(2.dp))
                Text("Travel | Photography | Good vibes only 📷", fontSize = 14.sp, lineHeight = 20.sp, color = N950)
                Text("Exploring new places and capturing memories 🌍", fontSize = 14.sp, lineHeight = 20.sp, color = N950)

                val meta = buildList { if (!p.gender.isNullOrBlank()) add(p.gender!!); if (p.age != null) add(p.age.toString()) }
                if (meta.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Box(Modifier.clip(RoundedCornerShape(999.dp)).background(N900).padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Text(meta.joinToString("  "), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = N50)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ═══════ TABS ════════════════════════════════════════
            Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(44.dp)
                .clip(RoundedCornerShape(999.dp)).background(N100).padding(4.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    TABS.forEachIndexed { i, label ->
                        val sel = i == tab
                        Box(Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(999.dp))
                            .background(if (sel) N900 else Color.Transparent, RoundedCornerShape(999.dp))
                            .clickable(remember { MutableInteractionSource() }, null) { tab = i },
                            contentAlignment = Alignment.Center
                        ) { Text(label, fontSize = 14.sp, lineHeight = 20.sp,
                            fontWeight = if (sel) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (sel) N50 else N500) }
                    }
                }
            }

            // ═══════ FEED CARD ═════════════════════════════════════
            Surface(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                shape = RoundedCornerShape(8.dp), color = Color.White, shadowElevation = 2.dp, tonalElevation = 0.5.dp) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(Modifier.size(36.dp), shape = CircleShape, color = Color.Transparent, shadowElevation = 4.dp) {
                                Box(Modifier.fillMaxSize().clip(CircleShape)) {
                                    AsyncImage(model = "https://images.unsplash.com/photo-1625241152315-4a698f74ceb7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                                        contentDescription = "Jacob", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                }
                            }
                            Column {
                                Text(atHandle, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp, color = N950)
                                Text("1 min ago", fontSize = 12.sp, lineHeight = 16.sp, color = N500)
                            }
                        }
                        Text("•••", fontSize = 16.sp, color = N500)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Took a break from the screen and went for a walk in the park 🌳☀️",
                        fontSize = 14.sp, lineHeight = 20.sp, color = N950)
                    Spacer(Modifier.height(4.dp))
                    Text("#travel #traveling #socialenvy #vacation",
                        fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium, color = N900)
                    Spacer(Modifier.height(8.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AsyncImage(model = "https://images.unsplash.com/photo-1520310809185-5cc119cf8b08?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                            contentDescription = "Travel", modifier = Modifier.weight(1f).height(128.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop)
                        AsyncImage(model = "https://images.unsplash.com/photo-1778461456551-2126d8a7fa67?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                            contentDescription = "Beach", modifier = Modifier.weight(1f).height(128.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop)
                    }

                    Spacer(Modifier.height(12.dp))
                    Box(Modifier.fillMaxWidth().height(1.dp).background(N200))
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("♡", fontSize = 20.sp, color = N500)
                            Text("120+", fontSize = 14.sp, lineHeight = 20.sp, color = N500)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("💬", fontSize = 16.sp, color = N500)
                            Text("65", fontSize = 14.sp, lineHeight = 20.sp, color = N500)
                        }
                        Text("↗", fontSize = 20.sp, color = N500)
                    }
                }
            }

            Spacer(Modifier.height(120.dp))
        }

        // Settings dropdown
        Box(Modifier.fillMaxWidth().statusBarsPadding().padding(top = 8.dp, end = 16.dp), contentAlignment = Alignment.TopEnd) {
            DropdownMenu(showSettings, { showSettings = false }, modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp))) {
                DropdownMenuItem(text = { Text("Log out", color = FunkyError, fontWeight = FontWeight.Medium) },
                    onClick = { showSettings = false; authViewModel.logout() })
            }
        }
    }
}
