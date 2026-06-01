package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.components.PremiumCard
import com.itsraj.funkytalk.ui.components.PremiumTextField
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.MangoYellow
import com.itsraj.funkytalk.viewmodel.DiscoverViewModel

private val POPULAR_LANGUAGES = listOf(
    "English", "Spanish", "French", "German",
    "Japanese", "Korean", "Chinese", "Portuguese"
)

@Composable
fun DiscoverScreen(navController: NavController) {
    val viewModel: DiscoverViewModel = viewModel()
    val profiles by viewModel.filteredProfiles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp)
        ) {
            item {
                Text(
                    "Discover",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    "Find your next language partner.",
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                PremiumTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.onSearchQueryChange(it)
                    },
                    label = "Search by username, country...",
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.Black.copy(alpha = 0.4f)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            label = "All",
                            isSelected = selectedLanguage == null,
                            onClick = {
                                selectedLanguage = null
                                viewModel.onLanguageSelected(null)
                            }
                        )
                    }
                    items(POPULAR_LANGUAGES) { lang ->
                        FilterChip(
                            label = lang,
                            isSelected = selectedLanguage == lang,
                            onClick = {
                                selectedLanguage = lang
                                viewModel.onLanguageSelected(lang)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            when {
                isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MangoYellow)
                        }
                    }
                }

                error != null -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Could not load users",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { viewModel.loadProfiles() },
                                    colors = ButtonDefaults.buttonColors(containerColor = MangoYellow)
                                ) {
                                    Text("Retry", color = Color.Black)
                                }
                            }
                        }
                    }
                }

                profiles.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "No users found",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Try a different search or filter.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                else -> {
                    items(profiles) { profile ->
                        PremiumCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clickable {
                                    navController.navigate(
                                        Screen.ChatDetail.createRoute(
                                            profile.username ?: "User",
                                            profile.avatar_url ?: ""
                                        )
                                    )
                                }
                        ) {
                            UserCardContent(profile = profile)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun UserCardContent(profile: com.itsraj.funkytalk.data.model.UserProfile) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        if (profile.avatar_url != null) {
            AsyncImage(
                model = profile.avatar_url,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (profile.username?.take(2) ?: "?").uppercase(),
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = Color.Black.copy(alpha = 0.3f)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = profile.profile_name ?: profile.username ?: "User",
                fontWeight = FontWeight.Black,
                color = Color.Black,
                fontSize = 18.sp
            )
            Text(
                text = buildLanguageSubtitle(
                    native = profile.native_languages,
                    learning = profile.learning_languages
                ),
                color = Color.Black.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
    }

    // Interests
    val hobbies = profile.hobbies
    if (!hobbies.isNullOrEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            hobbies.take(3).forEach { hobby ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.05f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        hobby,
                        fontSize = 10.sp,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) MangoYellow else Color.Black.copy(alpha = 0.05f))
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

private fun buildLanguageSubtitle(native: List<String>?, learning: List<String>?): String {
    val parts = mutableListOf<String>()
    native?.let { if (it.isNotEmpty()) parts.add("Native: ${it.take(2).joinToString(", ")}") }
    learning?.let { if (it.isNotEmpty()) parts.add("Learning: ${it.take(2).joinToString(", ")}") }
    return if (parts.isEmpty()) "No languages listed" else parts.joinToString(" • ")
}
