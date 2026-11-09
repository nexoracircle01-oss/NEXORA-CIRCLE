package com.nexora.circle.ui

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexora.circle.ui.screens.AppearanceScreen
import com.nexora.circle.ui.screens.BeemScreen
import com.nexora.circle.ui.screens.FeedScreen
import com.nexora.circle.ui.screens.OrbitScreen
import com.nexora.circle.ui.screens.ProfileScreen
import com.nexora.circle.ui.screens.SettingsScreen
import com.nexora.circle.ui.theme.NexoraTheme
import com.nexora.circle.ui.theme.ThemeMode
import com.nexora.circle.ui.theme.ThemeViewModel

enum class Destination(val route: String, val label: String, val icon: ImageVector) {
    PROFILE("profile", "Profile", Icons.Outlined.Person),
    ORBIT("orbit", "Orbit", Icons.Outlined.Public),
    FEED("feed", "Feed", Icons.Outlined.Explore),
    BEEM("beem", "Beem", Icons.Outlined.ChatBubbleOutline),
    SETTINGS("settings", "Settings", Icons.Outlined.Settings)
}

private const val ROUTE_APPEARANCE = "settings/appearance"

@Composable
fun NexoraApp(themeViewModel: ThemeViewModel = viewModel()) {
    val settings by themeViewModel.settings.collectAsStateWithLifecycle()

    // Keep system bar icons readable for the chosen theme.
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        val controller = WindowCompat.getInsetsController(window, view)
        val light = settings.mode == ThemeMode.LIGHT
        controller.isAppearanceLightStatusBars = light
        controller.isAppearanceLightNavigationBars = light
    }

    NexoraTheme(settings) {
        val nav = rememberNavController()
        val backStackEntry by nav.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavigationBar {
                    Destination.entries.forEach { d ->
                        NavigationBarItem(
                            selected = currentRoute?.startsWith(d.route) == true,
                            onClick = {
                                nav.navigate(d.route) {
                                    popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(d.icon, contentDescription = d.label) },
                            label = { Text(d.label) }
                        )
                    }
                }
            }
        ) { inner ->
            NavHost(
                navController = nav,
                startDestination = Destination.PROFILE.route,
                modifier = Modifier.padding(inner)
            ) {
                composable(Destination.PROFILE.route) { ProfileScreen() }
                composable(Destination.ORBIT.route) { OrbitScreen() }
                composable(Destination.FEED.route) { FeedScreen() }
                composable(Destination.BEEM.route) { BeemScreen() }
                composable(Destination.SETTINGS.route) {
                    SettingsScreen(onOpenAppearance = { nav.navigate(ROUTE_APPEARANCE) })
                }
                composable(ROUTE_APPEARANCE) {
                    AppearanceScreen(
                        current = settings,
                        onSave = themeViewModel::save,
                        onBack = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}
