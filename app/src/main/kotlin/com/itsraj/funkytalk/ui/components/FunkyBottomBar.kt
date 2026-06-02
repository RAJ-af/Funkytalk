package com.itsraj.funkytalk.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.itsraj.funkytalk.ui.navigation.Screen
import com.itsraj.funkytalk.ui.theme.*

@Composable
fun FunkyBottomNavigation(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = listOf(
        NavigationItem("Home", Screen.Home.route, Icons.Outlined.Home),
        NavigationItem("Moments", Screen.Moments.route, Icons.Outlined.AutoAwesome),
        NavigationItem("Discover", Screen.Discover.route, Icons.Outlined.Explore),
        NavigationItem("Chats", Screen.Chats.route, Icons.Outlined.ChatBubbleOutline),
        NavigationItem("Profile", Screen.Profile.route, Icons.Outlined.PersonOutline)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Sizes.bottomNavHeight + 16.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = FunkySurface.copy(alpha = 0.95f),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, FunkyBorderLight)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) FunkyYellow else FunkyTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                    if (isSelected) {
                        Spacer(Modifier.height(2.dp))
                        Box(Modifier.size(4.dp).clip(CircleShape).background(FunkyYellow))
                    }
                }
            }
        }
    }
}

data class NavigationItem(val title: String, val route: String, val icon: ImageVector)
