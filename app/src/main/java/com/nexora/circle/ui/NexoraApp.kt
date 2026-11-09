package com.nexora.circle.ui

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexora.circle.ui.components.NexoraBottomBar
import com.nexora.circle.ui.components.NexoraTopBar
import com.nexora.circle.ui.navigation.Destination
import com.nexora.circle.ui.navigation.Routes
import com.nexora.circle.ui.screens.AppearanceScreen
import com.nexora.circle.ui.screens.BeamsScreen
import com.nexora.circle.ui.screens.NotificationsScreen
import com.nexora.circle.ui.screens.OrbitScreen
import com.nexora.circle.ui.screens.ProfileScreen
import com.nexora.circle.ui.screens.SettingsScreen
import com.nexora.circle.ui.theme.NexoraTheme
import com.nexora.circle.ui.theme.ThemeMode
import com.nexora.circle.ui.theme.ThemeViewModel

@Composable
fun NexoraApp(
    requestedRoute: String? = null,
    onRouteHandled: () -> Unit = {},
    themeViewModel: ThemeViewModel = viewModel()
) {
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
        val currentTab = Destination.entries.firstOrNull { it.route == currentRoute }

        fun openTab(d: Destination) {
            nav.navigate(d.route) {
                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }

        // Launcher shortcuts: only known tab routes are accepted.
        LaunchedEffect(requestedRoute) {
            if (requestedRoute != null) {
                Destination.entries.firstOrNull { it.route == requestedRoute }
                    ?.let { openTab(it) }
                onRouteHandled()
            }
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                if (currentTab != null) {
                    NexoraTopBar(
                        title = currentTab.label,
                        onSettingsClick = {
                            nav.navigate(Routes.SETTINGS) { launchSingleTop = true }
                        }
                    )
                }
            },
            bottomBar = {
                NexoraBottomBar(
                    currentRoute = currentRoute,
                    onSelect = { openTab(it) }
                )
            }
        ) { inner ->
            NavHost(
                navController = nav,
                startDestination = Destination.ORBIT.route,
                modifier = Modifier.padding(inner)
            ) {
                composable(Destination.ORBIT.route) { OrbitScreen() }
                composable(Destination.BEAMS.route) { BeamsScreen() }
                composable(Destination.NOTIFICATIONS.route) { NotificationsScreen() }
                composable(Destination.PROFILE.route) { ProfileScreen() }
                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        onBack = { nav.popBackStack() },
                        onOpenAppearance = { nav.navigate(Routes.APPEARANCE) }
                    )
                }
                composable(Routes.APPEARANCE) {
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
