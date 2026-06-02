package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsraj.funkytalk.ui.components.*
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
            TopAppBar(
                title = { Text("Feedback", style = AppTextStyle.headline.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = FunkyTextPrimary)
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Tell us how we can improve FunkyTalk. We appreciate your feedback!",
                style = AppTextStyle.body.copy(color = FunkyTextSecondary, fontSize = 14.sp)
            )

            GroupedCard(title = "Category") {
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isMenuExpanded = true }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(category, style = AppTextStyle.body.copy(fontSize = 15.sp, color = FunkyTextPrimary))
                        Icon(Icons.Default.ArrowDropDown, null, tint = FunkyTextTertiary)
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                        modifier = Modifier.background(FunkySurfaceElevated).clip(RoundedCornerShape(16.dp))
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, style = AppTextStyle.body) },
                                onClick = { category = cat; isMenuExpanded = false }
                            )
                        }
                    }
                }
            }

            GroupedCard(title = "Your Message") {
                BasicTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                    textStyle = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 15.sp),
                    cursorBrush = SolidColor(FunkyYellow),
                    decorationBox = { innerTextField ->
                        Box {
                            if (message.isEmpty()) {
                                Text("What's on your mind?", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 15.sp))
                            }
                            innerTextField()
                        }
                    }
                )
            }

            GroupedCard(title = "Contact Email (Optional)") {
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = AppTextStyle.body.copy(color = FunkyTextPrimary, fontSize = 15.sp),
                    cursorBrush = SolidColor(FunkyYellow),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {
                            if (email.isEmpty()) {
                                Text("email@example.com", style = AppTextStyle.body.copy(color = FunkyTextTertiary, fontSize = 15.sp))
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { /* Submit */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FunkyYellow, contentColor = FunkyTextOnYellow)
            ) {
                Text("Submit Feedback", style = AppTextStyle.label.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            }

            Text(
                "We read every feedback carefully and use it to make FunkyTalk better.",
                style = AppTextStyle.caption.copy(color = FunkyTextTertiary, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
