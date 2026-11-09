package com.nexora.circle.ui.screens

import androidx.compose.runtime.Composable
import com.nexora.circle.ui.components.StageScreen

@Composable
fun OrbitScreen() = StageScreen(
    title = "Orbit",
    stage = 4,
    summary = "Join, create and moderate communities here."
)

@Composable
fun BeamsScreen() = StageScreen(
    title = "Beams",
    stage = 6,
    summary = "Private one-to-one and group Beams will live here."
)

@Composable
fun NotificationsScreen() = StageScreen(
    title = "Notifications",
    stage = 6,
    summary = "Your alerts and activity will appear here."
)

@Composable
fun ProfileScreen() = StageScreen(
    title = "Profile",
    stage = 3,
    summary = "Your profile, achievements, Signals and privacy controls will live here."
)
