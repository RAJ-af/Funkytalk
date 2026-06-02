package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
                title = { Text("Feedback", style = AppTextStyle.titleLarge.copy(fontWeight = FontWeight.SemiBold)) },
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
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Let us know how we can improve.", style = AppTextStyle.body.copy(color = FunkyTextSecondary))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Category", style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
                Box {
                    Surface(
                        onClick = { isMenuExpanded = true },
                        shape = RoundedCornerShape(12.dp),
                        color = FunkySurfaceElevated,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        border = BorderStroke(1.dp, FunkyBorder)
                    ) {
                        Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(category, style = AppTextStyle.body.copy(fontSize = 14.sp))
                            Icon(Icons.Default.ArrowDropDown, null, tint = FunkyTextSecondary)
                        }
                    }
                    DropdownMenu(expanded = isMenuExpanded, onDismissRequest = { isMenuExpanded = false }, modifier = Modifier.background(FunkySurface)) {
                        categories.forEach { cat ->
                            DropdownMenuItem(text = { Text(cat, style = AppTextStyle.body) }, onClick = { category = cat; isMenuExpanded = false })
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Message", style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    placeholder = { Text("What's on your mind?", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 14.sp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FunkyYellow,
                        unfocusedBorderColor = FunkyBorder,
                        unfocusedContainerColor = FunkySurfaceElevated,
                        focusedContainerColor = FunkySurfaceElevated
                    ),
                    textStyle = AppTextStyle.body.copy(fontSize = 14.sp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Email (Optional)", style = AppTextStyle.labelSmall.copy(color = FunkyTextSecondary))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    placeholder = { Text("email@example.com", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 14.sp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FunkyYellow,
                        unfocusedBorderColor = FunkyBorder,
                        unfocusedContainerColor = FunkySurfaceElevated,
                        focusedContainerColor = FunkySurfaceElevated
                    ),
                    singleLine = true,
                    textStyle = AppTextStyle.body.copy(fontSize = 14.sp)
                )
            }

            Spacer(Modifier.height(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* Submit */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow, contentColor = FunkyTextOnYellow)
                ) {
                    Text("Submit Feedback", style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold))
                }
                Text("We read every feedback carefully.", style = AppTextStyle.caption.copy(color = FunkyTextTertiary))
            }
        }
    }
}
