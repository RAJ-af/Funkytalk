package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.components.*
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    var username by remember { mutableStateOf("Raj Kumar") }
    var bio by remember { mutableStateOf("Explorer 🌍 | Tech Enthusiast 💻 | Coffee Addict ☕️\nAlways looking for new friends to chat with!") }
    var nativeLanguages by remember { mutableStateOf(listOf("Hindi")) }
    var learningLanguages by remember { mutableStateOf(listOf("English")) }
    var interests by remember { mutableStateOf(listOf("Music", "Gaming", "Travel", "Coding", "Anime")) }

    var showInterestSheet by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = FunkyBackground,
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", style = AppTextStyle.headline.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Save", color = FunkyYellow, style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FunkyBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Avatar
            Box {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, FunkyBorderLight)
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                IconButton(
                    onClick = { /* Change Avatar */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(FunkyYellow)
                ) {
                    Icon(Icons.Default.CameraAlt, null, tint = FunkyTextOnYellow, modifier = Modifier.size(16.dp))
                }
            }

            GroupedCard(title = "Basic Info") {
                EditField(label = "Username", value = username, onValueChange = { username = it })
                HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(vertical = 12.dp))
                EditField(label = "Bio", value = bio, onValueChange = { bio = it }, singleLine = false)
            }

            GroupedCard(title = "Languages") {
                LanguageSelectionRow(
                    label = "Native",
                    languages = nativeLanguages,
                    onAdd = { showLanguageSheet = "Native" }
                )
                HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(vertical = 12.dp))
                LanguageSelectionRow(
                    label = "Learning",
                    languages = learningLanguages,
                    onAdd = { showLanguageSheet = "Learning" }
                )
            }

            GroupedCard(title = "Interests") {
                InterestsFlow(interests) { showInterestSheet = true }
            }

            Spacer(Modifier.height(40.dp))
        }

        if (showInterestSheet) {
            InterestSelectionSheet(
                selectedInterests = interests,
                onDismiss = { showInterestSheet = false },
                onInterestsSelected = { interests = it }
            )
        }

        if (showLanguageSheet != null) {
            LanguageSelectionSheet(
                type = showLanguageSheet!!,
                selectedLanguages = if (showLanguageSheet == "Native") nativeLanguages else learningLanguages,
                onDismiss = { showLanguageSheet = null },
                onLanguageSelected = { lang ->
                    if (showLanguageSheet == "Native") {
                        if (!nativeLanguages.contains(lang)) nativeLanguages = nativeLanguages + lang
                    } else {
                        if (!learningLanguages.contains(lang)) learningLanguages = learningLanguages + lang
                    }
                }
            )
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit, singleLine: Boolean = true) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontSize = 11.sp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 15.sp),
            cursorBrush = SolidColor(FunkyYellow),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text("Enter $label", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 15.sp))
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun LanguageSelectionRow(label: String, languages: List<String>, onAdd: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontSize = 11.sp))
            if (languages.isEmpty()) {
                Text("None added", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 14.sp))
            } else {
                Text(languages.joinToString(", "), style = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 14.sp))
            }
        }
        IconButton(onClick = onAdd) {
            Icon(Icons.Default.Add, null, tint = FunkyYellow)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsFlow(interests: List<String>, onEdit: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FlowRow(
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
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary, fontSize = 12.sp)
                    )
                }
            }
            IconButton(
                onClick = onEdit,
                modifier = Modifier.size(32.dp).clip(CircleShape).background(FunkySurfaceElevated)
            ) {
                Icon(Icons.Default.Edit, null, modifier = Modifier.size(14.dp), tint = FunkyTextSecondary)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestSelectionSheet(
    selectedInterests: List<String>,
    onDismiss: () -> Unit,
    onInterestsSelected: (List<String>) -> Unit
) {
    val allInterests = listOf("Music", "Travel", "Coding", "Gaming", "Photography", "Art", "Movies", "Reading", "Sports", "Cooking", "Fashion", "Fitness")
    var currentSelected by remember { mutableStateOf(selectedInterests) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkySurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Text("Select Interests", style = AppTextStyle.headline.copy(fontSize = 18.sp), modifier = Modifier.padding(bottom = 12.dp))

            LazyColumn(modifier = Modifier.heightIn(max = 400.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(allInterests) { interest ->
                    val isSelected = currentSelected.contains(interest)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) FunkyYellowSoft else FunkySurfaceElevated,
                        modifier = Modifier.fillMaxWidth().clickable {
                            currentSelected = if (isSelected) currentSelected - interest else currentSelected + interest
                        }
                    ) {
                        Row(Modifier.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(interest, style = AppTextStyle.label.copy(color = FunkyTextPrimary))
                            if (isSelected) {
                                Spacer(Modifier.weight(1f))
                                Icon(Icons.Default.Check, null, tint = FunkyYellow, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onInterestsSelected(currentSelected); onDismiss() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow, contentColor = FunkyTextOnYellow)
            ) {
                Text("Confirm", style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionSheet(
    type: String,
    selectedLanguages: List<String>,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val allLangs = listOf("English", "Spanish", "French", "German", "Chinese", "Japanese", "Korean", "Hindi")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkySurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Text("Select $type Language", style = AppTextStyle.headline.copy(fontSize = 18.sp), modifier = Modifier.padding(bottom = 12.dp))

            LazyColumn(modifier = Modifier.heightIn(max = 400.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(allLangs) { lang ->
                    val isSelected = selectedLanguages.contains(lang)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) FunkyYellowSoft else FunkySurfaceElevated,
                        modifier = Modifier.fillMaxWidth().clickable(enabled = !isSelected) {
                            onLanguageSelected(lang)
                            onDismiss()
                        }
                    ) {
                        Row(Modifier.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(lang, style = AppTextStyle.label.copy(color = if (isSelected) FunkyTextTertiary else FunkyTextPrimary))
                            if (isSelected) {
                                Spacer(Modifier.weight(1f))
                                Text("Added", style = AppTextStyle.caption.copy(color = FunkyTextSecondary))
                            }
                        }
                    }
                }
            }
        }
    }
}
