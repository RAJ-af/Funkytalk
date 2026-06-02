package com.itsraj.funkytalk.ui.screens

import androidx.compose.foundation.background
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
import com.itsraj.funkytalk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToTerms: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "About",
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(FunkyYellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "FT",
                    style = AppTextStyle.displayLarge.copy(
                        color = FunkyTextOnYellow,
                        fontWeight = FontWeight.Black
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "FunkyTalk",
                style = AppTextStyle.headline.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                "Connect with the world, funkily.",
                style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "FunkyTalk is a next-gen social platform designed for meaningful connections and vibrant voice rooms. Join communities, share moments, and explore a world of conversations.",
                style = AppTextStyle.body.copy(textAlign = TextAlign.Center, lineHeight = 22.sp),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(40.dp))

            // Info Rows
            AboutItem("Version", "1.0.0 (Stable)")
            HorizontalDivider(color = FunkyBorderLight)
            AboutItem("Privacy Policy", showChevron = true, onClick = onNavigateToPrivacy)
            HorizontalDivider(color = FunkyBorderLight)
            AboutItem("Terms of Service", showChevron = true, onClick = onNavigateToTerms)
            HorizontalDivider(color = FunkyBorderLight)
            AboutItem("Support Email", "support@funkytalk.com")

            Spacer(Modifier.height(40.dp))

            Text(
                "© 2025 FunkyTalk Inc.",
                style = AppTextStyle.caption.copy(color = FunkyTextTertiary)
            )
        }
    }
}

@Composable
fun AboutItem(
    label: String,
    value: String? = null,
    showChevron: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Surface(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                label,
                style = AppTextStyle.label.copy(color = FunkyTextPrimary, fontSize = 15.sp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (value != null) {
                    Text(
                        value,
                        style = AppTextStyle.bodySmall.copy(color = FunkyTextSecondary)
                    )
                }
                if (showChevron) {
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Outlined.ChevronRight,
                        null,
                        Modifier.size(18.dp),
                        tint = FunkyTextTertiary
                    )
                }
            }
        }
    }
}
