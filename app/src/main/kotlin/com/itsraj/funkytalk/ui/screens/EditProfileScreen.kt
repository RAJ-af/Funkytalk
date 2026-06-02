package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val userProfile by authViewModel.userProfile.collectAsState()
    val scrollState = rememberScrollState()

    var profileName by remember { mutableStateOf(userProfile?.profile_name ?: "") }
    var bio by remember { mutableStateOf(userProfile?.bio ?: "") }
    var nativeLangs by remember { mutableStateOf(userProfile?.native_languages ?: emptyList()) }
    var learningLangs by remember { mutableStateOf(userProfile?.learning_languages ?: emptyList()) }
    var hobbies by remember { mutableStateOf(userProfile?.hobbies ?: emptyList()) }

    var showInterestSheet by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile", style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.SemiBold)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = FunkyTextPrimary)
                    }
                },
                actions = {
                    TextButton(onClick = { /* Save logic */ }) {
                        Text("Save", style = AppTextStyle.label.copy(color = FunkyYellow, fontWeight = FontWeight.Bold))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = FunkyBackground)
            )
        },
        containerColor = FunkyBackground
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            // Header Image Area
            Box(Modifier.fillMaxWidth().height(180.dp)) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?w=1000",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp)
                ) {
                    AsyncImage(
                        model = userProfile?.avatar_url ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(3.dp, FunkyBackground, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    // Flag on Avatar
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(getFlagEmoji(userProfile?.country ?: "IN"), fontSize = 14.sp)
                    }
                }

                Surface(
                    onClick = { /* Change Banner */ },
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.align(Alignment.Center).offset(y = (-20).dp).size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }

            Column(Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                EditField("Display Name", profileName) { profileName = it }
                EditField("Bio", bio, singleLine = false) { bio = it }

                SectionHeaderLabel("Languages")
                LanguageRow("Native (Max 2)", nativeLangs, 2) { showLanguageSheet = "native" }
                LanguageRow("Learning (Max 3)", learningLangs, 3) { showLanguageSheet = "learning" }

                SectionHeaderLabel("Interests")
                InterestsGrid(hobbies) { showInterestSheet = true }
            }
        }

        if (showInterestSheet) {
            InterestSelectionSheet(
                selectedInterests = hobbies,
                onDismiss = { showInterestSheet = false },
                onInterestsSelected = { hobbies = it }
            )
        }

        if (showLanguageSheet != null) {
            LanguageSelectionSheet(
                type = showLanguageSheet!!,
                selectedLanguages = if (showLanguageSheet == "native") nativeLangs else learningLangs,
                onDismiss = { showLanguageSheet = null },
                onLanguageSelected = { selected ->
                    if (showLanguageSheet == "native") {
                        if (nativeLangs.size < 2) nativeLangs = nativeLangs + selected
                    } else {
                        if (learningLangs.size < 3) learningLangs = learningLangs + selected
                    }
                    showLanguageSheet = null
                }
            )
        }
    }
}

@Composable
fun EditField(label: String, value: String, singleLine: Boolean = true, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FunkyYellow,
                unfocusedBorderColor = FunkyBorder,
                unfocusedContainerColor = FunkySurfaceElevated,
                focusedContainerColor = FunkySurfaceElevated
            ),
            singleLine = singleLine,
            textStyle = AppTextStyle.body.copy(fontSize = 14.sp)
        )
    }
}

@Composable
fun SectionHeaderLabel(title: String) {
    Text(
        title,
        style = AppTextStyle.titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun LanguageRow(label: String, languages: List<String>, max: Int, onAdd: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(max) { index ->
                val lang = languages.getOrNull(index)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (lang != null) FunkyYellowSoft else FunkySurfaceElevated,
                    border = if (lang != null) null else BorderStroke(1.dp, FunkyBorder),
                    modifier = Modifier.height(40.dp).weight(1f).clickable(onClick = if (lang == null) onAdd else ({}))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (lang != null) {
                            Text(lang, style = AppTextStyle.label.copy(color = FunkyTextOnYellow, fontSize = 12.sp))
                        } else {
                            Icon(Icons.Default.Add, null, tint = FunkyTextTertiary, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsGrid(interests: List<String>, onEdit: () -> Unit) {
    Surface(
        color = FunkySurfaceElevated,
        shape = AppShapes.card,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onEdit)
    ) {
        Box(Modifier.padding(14.dp)) {
            if (interests.isEmpty()) {
                Text("Select your interests", style = AppTextStyle.bodySmall.copy(color = FunkyTextTertiary))
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    interests.forEach { interest ->
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, FunkyBorder)
                        ) {
                            Text(
                                interest,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary, fontSize = 11.sp)
                            )
                        }
                    }
                }
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
    var searchQuery by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkySurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) },
        shape = AppShapes.bottomSheet
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Text("Select Interests", style = AppTextStyle.headline.copy(fontSize = 18.sp), modifier = Modifier.padding(bottom = 12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...", style = AppTextStyle.body.copy(color = FunkyTextTertiary)) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Search, null, Modifier.size(18.dp)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FunkyYellow,
                    unfocusedBorderColor = FunkyBorder,
                    unfocusedContainerColor = FunkySurfaceElevated,
                    focusedContainerColor = FunkySurfaceElevated
                )
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.heightIn(max = 300.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val filtered = allInterests.filter { it.contains(searchQuery, ignoreCase = true) }
                items(filtered) { interest ->
                    val isSelected = currentSelected.contains(interest)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) FunkyYellowSoft else Color.Transparent,
                        modifier = Modifier.fillMaxWidth().clickable {
                            currentSelected = if (isSelected) currentSelected - interest else currentSelected + interest
                        }
                    ) {
                        Row(Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(interest, style = AppTextStyle.label.copy(color = FunkyTextPrimary, fontSize = 14.sp))
                            if (isSelected) {
                                Spacer(Modifier.weight(1f))
                                Icon(Icons.Default.Check, null, tint = FunkyYellow, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onInterestsSelected(currentSelected); onDismiss() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow, contentColor = FunkyTextOnYellow)
            ) {
                Text("Confirm", style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold))
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
    var searchQuery by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkySurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) },
        shape = AppShapes.bottomSheet
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Text("Select $type Language", style = AppTextStyle.headline.copy(fontSize = 18.sp), modifier = Modifier.padding(bottom = 12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...", style = AppTextStyle.body.copy(color = FunkyTextTertiary)) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Search, null, Modifier.size(18.dp)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FunkyYellow,
                    unfocusedBorderColor = FunkyBorder,
                    unfocusedContainerColor = FunkySurfaceElevated,
                    focusedContainerColor = FunkySurfaceElevated
                )
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.heightIn(max = 300.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val filtered = allLangs.filter { it.contains(searchQuery, ignoreCase = true) }
                items(filtered) { lang ->
                    val isSelected = selectedLanguages.contains(lang)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) FunkyYellowSoft else Color.Transparent,
                        modifier = Modifier.fillMaxWidth().clickable(enabled = !isSelected) {
                            onLanguageSelected(lang)
                        }
                    ) {
                        Row(Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(lang, style = AppTextStyle.label.copy(color = if (isSelected) FunkyTextTertiary else FunkyTextPrimary, fontSize = 14.sp))
                            if (isSelected) {
                                Spacer(Modifier.weight(1f))
                                Text("Selected", style = AppTextStyle.caption.copy(color = FunkyTextTertiary))
                            }
                        }
                    }
                }
            }
        }
    }
}
