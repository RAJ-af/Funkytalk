package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsraj.funkytalk.data.model.allLanguages
import com.itsraj.funkytalk.ui.theme.*
import com.itsraj.funkytalk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("Rajdeep Singh") }
    var bio by remember { mutableStateOf("Building something cool with Gen-Z vibes.") }
    var gender by remember { mutableStateOf("Male") }
    var birthday by remember { mutableStateOf("2002-05-15") }

    var nativeLanguages by remember { mutableStateOf(listOf("English", "Hindi")) }
    var learningLanguages by remember { mutableStateOf(listOf("French", "Japanese", "Spanish")) }
    var interests by remember { mutableStateOf(listOf("Coding", "Music", "Travel", "Gaming", "Photography")) }

    var showInterestSheet by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf<String?>(null) } // "native" or "learning"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", style = AppTextStyle.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Save", color = FunkyYellow, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FunkyBackground)
            )
        },
        containerColor = FunkyBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Media Section
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
                        model = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=1080",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { /* Edit Banner */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.CameraAlt, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }

                // Avatar
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp)
                        .size(100.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(4.dp, FunkyBackground),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&w=400",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { /* Edit Avatar */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.CameraAlt, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Basic Info
            EditSection(title = "Basic Info") {
                EditField(label = "Name", value = name, onValueChange = { name = it })
                EditField(label = "Bio", value = bio, onValueChange = { bio = it }, singleLine = false)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.weight(1f)) {
                        EditField(label = "Gender", value = gender, onValueChange = {}, readOnly = true, trailingIcon = Icons.Default.ArrowDropDown)
                    }
                    Box(Modifier.weight(1f)) {
                        EditField(label = "Birthday", value = birthday, onValueChange = {}, readOnly = true, trailingIcon = Icons.Default.CalendarToday)
                    }
                }
            }

            // Languages
            EditSection(title = "Languages") {
                LanguageSlotSection(
                    title = "Native",
                    languages = nativeLanguages,
                    maxSlots = 2,
                    onAdd = { showLanguageSheet = "native" }
                )
                Spacer(Modifier.height(16.dp))
                LanguageSlotSection(
                    title = "Learning",
                    languages = learningLanguages,
                    maxSlots = 3,
                    onAdd = { showLanguageSheet = "learning" }
                )
            }

            // Interests
            EditSection(title = "Interests") {
                InterestsGrid(interests = interests, onEdit = { showInterestSheet = true })
            }

            Spacer(Modifier.height(40.dp))
        }
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
            selectedLanguages = if (showLanguageSheet == "native") nativeLanguages else learningLanguages,
            onDismiss = { showLanguageSheet = null },
            onLanguageSelected = { lang ->
                if (showLanguageSheet == "native") {
                    if (nativeLanguages.size < 2) nativeLanguages = nativeLanguages + lang
                } else {
                    if (learningLanguages.size < 3) learningLanguages = learningLanguages + lang
                }
                showLanguageSheet = null
            }
        )
    }
}

@Composable
fun EditSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        Text(
            title,
            style = AppTextStyle.label.copy(
                color = FunkyTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
    }
}

@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    trailingIcon: ImageVector? = null
) {
    Column(Modifier.padding(bottom = 16.dp)) {
        Surface(
            color = FunkySurfaceElevated,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label, style = AppTextStyle.caption.copy(color = FunkyTextTertiary)) },
                readOnly = readOnly,
                singleLine = singleLine,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = FunkyTextPrimary,
                    unfocusedTextColor = FunkyTextPrimary
                ),
                trailingIcon = trailingIcon?.let { { Icon(it, null, tint = FunkyTextSecondary) } }
            )
        }
    }
}

@Composable
fun LanguageSlotSection(
    title: String,
    languages: List<String>,
    maxSlots: Int,
    onAdd: () -> Unit
) {
    Column {
        Text(
            title,
            style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(maxSlots) { index ->
                val lang = languages.getOrNull(index)
                if (lang != null) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = FunkyYellowSoft,
                        modifier = Modifier.height(44.dp).weight(1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(lang, style = AppTextStyle.label.copy(color = FunkyTextOnYellow))
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = FunkySurfaceElevated,
                        border = BorderStroke(1.dp, FunkyBorder),
                        modifier = Modifier
                            .height(44.dp)
                            .weight(1f)
                            .clickable(onClick = onAdd)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Add, null, tint = FunkyTextTertiary)
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
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().clickable(onClick = onEdit)
    ) {
        Column(Modifier.padding(16.dp)) {
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
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary)
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = FunkyYellowSoft,
                    modifier = Modifier.clickable(onClick = onEdit)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        null,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp).size(16.dp),
                        tint = FunkyTextOnYellow
                    )
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
    val allInterests = listOf(
        "Music", "Travel", "Coding", "Gaming", "Photography",
        "Art", "Movies", "Reading", "Sports", "Cooking",
        "Fashion", "Fitness", "Dance", "Writing", "Technology"
    )
    var searchQuery by remember { mutableStateOf("") }
    val filteredInterests = allInterests.filter { it.contains(searchQuery, ignoreCase = true) }
    var currentSelected by remember { mutableStateOf(selectedInterests) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkyBackground,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Select Interests",
                style = AppTextStyle.headline,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search interests...") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FunkyYellow,
                    unfocusedBorderColor = FunkyBorder
                )
            )

            LazyColumn(
                modifier = Modifier.heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredInterests) { interest ->
                    val isSelected = currentSelected.contains(interest)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) FunkyYellowSoft else FunkySurfaceElevated,
                        border = if (isSelected) BorderStroke(1.dp, FunkyYellow) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                currentSelected = if (isSelected) {
                                    currentSelected - interest
                                } else {
                                    currentSelected + interest
                                }
                            }
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(interest, style = AppTextStyle.label.copy(color = if (isSelected) FunkyTextOnYellow else FunkyTextPrimary))
                            if (isSelected) {
                                Icon(Icons.Default.Check, null, tint = FunkyTextOnYellow, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    onInterestsSelected(currentSelected)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow)
            ) {
                Text("Confirm Selection", color = FunkyTextOnYellow, fontWeight = FontWeight.Bold)
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
    var searchQuery by remember { mutableStateOf("") }
    val filteredLanguages = allLanguages.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = FunkyBackground,
        dragHandle = { BottomSheetDefaults.DragHandle(color = FunkyBorder) }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Select ${type.replaceFirstChar { it.uppercase() }} Language",
                style = AppTextStyle.headline,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search languages...") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FunkyYellow,
                    unfocusedBorderColor = FunkyBorder
                )
            )

            LazyColumn(
                modifier = Modifier.heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredLanguages) { lang ->
                    val isAlreadySelected = selectedLanguages.contains(lang.name)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isAlreadySelected) FunkySurfaceElevated.copy(alpha = 0.5f) else FunkySurfaceElevated,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isAlreadySelected) {
                                onLanguageSelected(lang.name)
                            }
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(lang.flag, fontSize = 20.sp)
                            Spacer(Modifier.width(12.dp))
                            Text(lang.name, style = AppTextStyle.label.copy(color = if (isAlreadySelected) FunkyTextTertiary else FunkyTextPrimary))
                            if (isAlreadySelected) {
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
