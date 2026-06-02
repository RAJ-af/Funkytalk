package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsraj.funkytalk.ui.components.*
import com.itsraj.funkytalk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit, onNavigateToPrivacy: () -> Unit, onNavigateToTerms: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About", style = AppTextStyle.headline.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)) },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(24.dp)).background(FunkyYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Text("FT", style = AppTextStyle.displayLarge.copy(color = FunkyTextOnYellow, fontWeight = FontWeight.Black, fontSize = 32.sp))
                }
                Spacer(Modifier.height(16.dp))
                Text("FunkyTalk", style = AppTextStyle.headline.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold))
                Text("Version 1.0.0", style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary))
            }

            GroupedCard {
                AboutRow("Privacy Policy", onClick = onNavigateToPrivacy)
                HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(vertical = 12.dp))
                AboutRow("Terms of Service", onClick = onNavigateToTerms)
                HorizontalDivider(color = FunkyBorderLight, modifier = Modifier.padding(vertical = 12.dp))
                AboutRow("Contact Support") { /* Support action */ }
            }

            Text(
                "FunkyTalk is a next-gen social platform for meaningful connections and vibrant voice rooms. Join communities, share moments, and explore the world funkily.",
                style = AppTextStyle.body.copy(textAlign = TextAlign.Center, lineHeight = 22.sp, color = FunkyTextSecondary, fontSize = 14.sp),
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.weight(1f))
            Text("© 2025 FunkyTalk Inc.", style = AppTextStyle.caption.copy(color = FunkyTextTertiary))
        }
    }
}

@Composable
fun AboutRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = AppTextStyle.label.copy(color = FunkyTextPrimary, fontSize = 15.sp))
        Icon(Icons.Outlined.ChevronRight, null, Modifier.size(18.dp), tint = FunkyTextTertiary)
    }
}
