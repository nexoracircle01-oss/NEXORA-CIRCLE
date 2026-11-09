package com.nexora.circle.ui.screens

import androidx.compose.runtime.Composable
import com.nexora.circle.ui.components.StageScreen

@Composable
fun ProfileScreen() = StageScreen(
    title = "Profile",
    stage = 3,
    summary = "Your profile, achievements, Signals and privacy controls will live here."
)

@Composable
fun OrbitScreen() = StageScreen(
    title = "Orbit",
    stage = 4,
    summary = "Join, create and moderate communities here."
)

@Composable
fun FeedScreen() = StageScreen(
    title = "Feed",
    stage = 5,
    summary = "Discover Signals and send your own here."
)

@Composable
fun BeemScreen() = StageScreen(
    title = "Beem",
    stage = 6,
    summary = "Private one-to-one and group Beems will live here."
)
