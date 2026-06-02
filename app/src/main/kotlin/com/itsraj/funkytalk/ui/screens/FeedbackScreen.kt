package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsraj.funkytalk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(onBack: () -> Unit) {
    var category by remember { mutableStateOf("Bug Report") }
    var message by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isMenuExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Bug Report", "Feature Request", "Account Issue", "Other")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Feedback",
                        style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = FunkyTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = FunkyBackground)
            )
        },
        containerColor = FunkyBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "We'd love to hear from you! Let us know how we can improve FunkyTalk.",
                style = AppTextStyle.body.copy(color = FunkyTextSecondary)
            )

            // Category Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Category",
                    style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary, fontWeight = FontWeight.SemiBold)
                )
                Box {
                    OutlinedCard(
                        onClick = { isMenuExpanded = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = FunkySurfaceElevated),
                        border = CardDefaults.outlinedCardBorder(enabled = true).copy(width = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(category, style = AppTextStyle.body.copy(color = FunkyTextPrimary))
                            Icon(Icons.Default.ArrowDropDown, null, tint = FunkyTextSecondary)
                        }
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                        modifier = Modifier.background(FunkySurface)
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, style = AppTextStyle.body) },
                                onClick = {
                                    category = cat
                                    isMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Message field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Message",
                    style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary, fontWeight = FontWeight.SemiBold)
                )
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    placeholder = { Text("What's on your mind?", style = AppTextStyle.body.copy(color = FunkyTextTertiary)) },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = FunkySurfaceElevated,
                        unfocusedContainerColor = FunkySurfaceElevated,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = FunkyYellow
                    ),
                    textStyle = AppTextStyle.body
                )
            }

            // Optional Email field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Email (Optional)",
                    style = AppTextStyle.labelSmall.copy(color = FunkyTextPrimary, fontWeight = FontWeight.SemiBold)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("email@example.com", style = AppTextStyle.body.copy(color = FunkyTextTertiary)) },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = FunkySurfaceElevated,
                        unfocusedContainerColor = FunkySurfaceElevated,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = FunkyYellow
                    ),
                    singleLine = true,
                    textStyle = AppTextStyle.body
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { /* Submit */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow, contentColor = FunkyTextOnYellow),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text("Submit Feedback", style = AppTextStyle.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}
