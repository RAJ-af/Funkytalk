package com.itsraj.funkytalk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// ─── Spacing Scale ─────────────────────────────────────────
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24.dp
    val xxxl = 32.dp
    val xxxxl = 40.dp
    val xxxxxl = 48.dp
    val section = xxl       // Between sections in a screen
    val element = lg        // Between elements within a section
    val inner = md          // Between items within a card/chip
    val screenEdge = xl     // Screen horizontal padding
    val contentTop = xxl    // Content top padding
}

// ─── Corner Radii ──────────────────────────────────────────
object Radii {
    val xs = 4.dp
    val sm = 6.dp
    val md = 8.dp
    val lg = 12.dp
    val xl = 16.dp
    val xxl = 20.dp
    val xxxl = 24.dp
    val pill = 999.dp
}

// ─── Component Sizes ───────────────────────────────────────
object Sizes {
    // Avatar
    val avatarXs = 24.dp
    val avatarSm = 28.dp
    val avatarMd = 32.dp
    val avatarLg = 40.dp
    val avatarXl = 56.dp
    val avatarXxl = 76.dp
    val avatarHero = 88.dp

    // Card
    val cardPaddingSm = 12.dp
    val cardPaddingMd = 16.dp
    val cardPaddingLg = 20.dp

    // Bottom navigation
    val bottomNavHeight = 72.dp
    val bottomNavMarginHorizontal = 16.dp
    val bottomNavMarginBottom = 12.dp

    // Cover
    val coverHeight = 120.dp

    // Tab bar
    val tabBarHeight = 40.dp

    // Icon
    val iconSm = 14.dp
    val iconMd = 18.dp
    val iconLg = 22.dp
}

// ─── Elevation / Shadow ────────────────────────────────────
object Elevations {
    val card = 2.dp          // Standard card (shadowElevation)
    val cardHover = 4.dp     // Card on press
    val bottomNav = 8.dp     // Floating nav
    val modal = 16.dp        // Modal / bottom sheet
    val chip = 0.dp          // Chips have no elevation (flat design)
}

// ─── Material3 Shapes ──────────────────────────────────────
object AppShapes {
    val none = RoundedCornerShape(0.dp)
    val xs = RoundedCornerShape(Radii.xs)
    val sm = RoundedCornerShape(Radii.sm)
    val md = RoundedCornerShape(Radii.md)
    val lg = RoundedCornerShape(Radii.lg)
    val xl = RoundedCornerShape(Radii.xl)
    val xxl = RoundedCornerShape(Radii.xxl)
    val xxxl = RoundedCornerShape(Radii.xxxl)
    val pill = RoundedCornerShape(Radii.pill)

    // Semantic shapes for card surfaces
    val card = xxl
    val chip = pill
    val avatar = RoundedCornerShape(Radii.pill) // Circle
    val bottomNav = xxl
    val tabBar = md
    val tabPill = sm

    // Material3 shapes mapping
    val material: Shapes = Shapes(
        extraSmall = xs,
        small = sm,
        medium = md,
        large = lg,
        extraLarge = xl
    )
}
