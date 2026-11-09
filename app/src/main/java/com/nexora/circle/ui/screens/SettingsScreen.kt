package com.nexora.circle.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val laterItems = listOf(
    "Account",
    "Privacy & Security",
    "Login & Authentication",
    "Two-Factor Authentication",
    "Anonymous Mode",
    "Notifications",
    "Blocked Accounts",
    "Language",
    "Data & Storage",
    "Delete Account",
    "Help & Support",
    "About NEXORA-CIRCLE"
)

@Composable
fun SettingsScreen(onOpenAppearance: () -> Unit) {
    val transparent = ListItemDefaults.colors(containerColor = Color.Transparent)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 12.dp)
            )
        }
        item {
            ListItem(
                headlineContent = { Text("Appearance & Theme") },
                supportingContent = { Text("Mode, accent color, card shape and text size") },
                colors = transparent,
                modifier = Modifier.clickable(onClick = onOpenAppearance)
            )
            HorizontalDivider()
        }
        laterItems.forEach { name ->
            item {
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text("Coming in a later stage") },
                    colors = transparent,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        }
    }
}
