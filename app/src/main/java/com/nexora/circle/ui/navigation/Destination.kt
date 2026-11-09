package com.nexora.circle.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.ui.graphics.vector.ImageVector

/** Bottom bar tabs, left to right. Settings lives in the top bar. */
enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    ORBIT("orbit", "Orbit", Icons.Outlined.Public, Icons.Filled.Public),
    BEAMS("beams", "Beams", Icons.Outlined.ChatBubbleOutline, Icons.Filled.ChatBubble),
    NOTIFICATIONS("notifications", "Notifications", Icons.Outlined.Notifications, Icons.Filled.Notifications),
    PROFILE("profile", "Profile", Icons.Outlined.Person, Icons.Filled.Person)
}

object Routes {
    const val SETTINGS = "settings"
    const val APPEARANCE = "settings/appearance"
}
