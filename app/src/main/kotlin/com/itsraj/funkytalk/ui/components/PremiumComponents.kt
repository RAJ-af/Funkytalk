package com.itsraj.funkytalk.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsraj.funkytalk.ui.theme.*
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MeshGradient(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "mesh")

    val xOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 100f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing), RepeatMode.Reverse), label = "x1"
    )
    val yOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 50f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Reverse), label = "y1"
    )

    val xOffset2 by infiniteTransition.animateFloat(
        initialValue = 100f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Reverse), label = "x2"
    )
    val yOffset2 by infiniteTransition.animateFloat(
        initialValue = 50f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing), RepeatMode.Reverse), label = "y2"
    )

    Box(modifier = modifier.clip(RoundedCornerShape(0.dp))) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = xOffset1, top = yOffset1) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(MeshPink.copy(alpha = 0.8f), Color.Transparent),
                        center = center,
                        radius = size.maxDimension * 0.8f
                    ),
                    radius = size.maxDimension * 0.8f,
                    center = center.copy(x = size.width * 0.2f, y = size.height * 0.2f)
                )
            }
            translate(left = xOffset2, top = yOffset2) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(MeshPurple.copy(alpha = 0.7f), Color.Transparent),
                        center = center,
                        radius = size.maxDimension * 0.8f
                    ),
                    radius = size.maxDimension * 0.8f,
                    center = center.copy(x = size.width * 0.8f, y = size.height * 0.3f)
                )
            }
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(MeshCyan.copy(alpha = 0.6f), Color.Transparent),
                    center = center,
                    radius = size.maxDimension * 0.7f
                ),
                radius = size.maxDimension * 0.7f,
                center = center.copy(x = size.width * 0.5f, y = size.height * 0.7f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(MeshYellow.copy(alpha = 0.5f), Color.Transparent),
                    center = center,
                    radius = size.maxDimension * 0.6f
                ),
                radius = size.maxDimension * 0.6f,
                center = center.copy(x = size.width * 0.3f, y = size.height * 0.8f)
            )
        }
    }
}

@Composable
fun GroupedCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        if (title != null) {
            Text(
                text = title,
                style = AppTextStyle.labelSmall.copy(
                    color = FunkyTextSecondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = FunkySurfaceElevated,
            border = BorderStroke(1.dp, FunkyBorderLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun GlassyBottomDock(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(64.dp)
            .fillMaxWidth(),
        color = Color.White.copy(alpha = 0.8f),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            content()
        }
    }
}

// ─── Restoring Legacy Premium Components to fix build ─────

@Composable
fun PremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MangoYellow,
    contentColor: Color = Color.Black,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "scale")

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(28.dp))
            .background(
                color = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.5f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) contentColor else contentColor.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label, fontWeight = FontWeight.Medium) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black.copy(alpha = 0.15f),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black.copy(alpha = 0.4f),
            cursorColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true
    )
}

@Composable
fun PremiumCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            content()
        }
    }
}

@Composable
fun VerticalWheelPicker(
    modifier: Modifier = Modifier,
    count: Int,
    initialIndex: Int = 0,
    onIndexChanged: (Int) -> Unit,
    itemHeight: Dp = 64.dp,
    content: @Composable (index: Int, isSelected: Boolean) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                onIndexChanged(index)
            }
    }

    Box(
        modifier = modifier
            .height(itemHeight * 3)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(itemHeight)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(16.dp))
                .background(MangoYellow.copy(alpha = 0.1f))
                .border(1.dp, MangoYellow.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = itemHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count) { index ->
                val isSelected = remember {
                    derivedStateOf { listState.firstVisibleItemIndex == index }
                }.value

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    content(index, isSelected)
                }
            }
        }
    }
}

@Composable
fun LanguageBadge(
    code: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFE3F2FD),
    textColor: Color = Color(0xFF1976D2)
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Text(
            text = code.uppercase(),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                fontSize = 10.sp
            ),
            color = textColor
        )
    }
}

@Composable
fun ModernLanguageChip(
    text: String,
    flag: String,
    code: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(if (isSelected) MangoYellow else Color.Black.copy(alpha = 0.05f), label = "bg")
    val textColor by animateColorAsState(if (isSelected) Color.Black else Color.Black.copy(alpha = 0.8f), label = "text")
    val scale by animateFloatAsState(if (isSelected) 1.05f else 1f, label = "scale")

    Surface(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        border = if (isSelected) null else BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LanguageBadge(
                code = code,
                backgroundColor = if (isSelected) Color.Black.copy(alpha = 0.1f) else Color.White,
                textColor = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(flag, fontSize = 16.sp)
        }
    }
}

@Composable
fun GenderIconButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(if (isSelected) MangoYellow else Color.Black.copy(alpha = 0.05f), label = "border")
    val backgroundColor by animateColorAsState(if (isSelected) MangoYellow.copy(alpha = 0.1f) else Color.Transparent, label = "bg")
    val scale by animateFloatAsState(if (isSelected) 1.1f else 1f, label = "scale")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(backgroundColor)
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = borderColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = if (isSelected) MangoYellow else Color.Black.copy(alpha = 0.3f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = label,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.5f),
            fontSize = 16.sp
        )
    }
}
