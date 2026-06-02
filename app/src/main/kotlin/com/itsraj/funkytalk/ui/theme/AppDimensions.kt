package com.itsraj.funkytalk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
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
    val section = 20.dp       // Between sections (compact)
    val element = 12.dp        // Between elements (compact)
    val inner = 10.dp          // Between items within a card
    val screenEdge = 20.dp     // Screen horizontal padding
    val contentTop = 24.dp
}

// ─── Corner Radii ──────────────────────────────────────────
object Radii {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24.dp
    val xxxl = 32.dp
    val pill = 999.dp
}

// ─── Component Sizes ───────────────────────────────────────
object Sizes {
    // Avatar
    val avatarXs = 24.dp
    val avatarSm = 32.dp
    val avatarMd = 48.dp
    val avatarLg = 64.dp
    val avatarXl = 80.dp
    val avatarHero = 100.dp
    val avatarXxl = 76.dp

    // Card
    val cardPaddingSm = 12.dp
    val cardPaddingMd = 16.dp
    val cardPaddingLg = 20.dp

    // Bottom navigation
    val bottomNavHeight = 64.dp
    val bottomNavMarginHorizontal = 16.dp
    val bottomNavMarginBottom = 12.dp

    // Cover
    val coverHeight = 180.dp

    // Tab bar
    val tabBarHeight = 44.dp

    // Icon
    val iconSm = 16.dp
    val iconMd = 20.dp
    val iconLg = 24.dp
}

// ─── Elevation / Shadow ────────────────────────────────────
object Elevations {
    val card = 0.dp
    val cardHover = 0.dp
    val bottomNav = 0.dp
    val modal = 16.dp
    val chip = 0.dp
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

    // Semantic shapes
    val card = xl
    val chip = pill
    val avatar = pill
    val bottomNav = xxl
    val bottomSheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
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
