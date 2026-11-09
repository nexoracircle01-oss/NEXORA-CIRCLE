package com.nexora.circle.ui.components

import android.view.SoundEffectConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexora.circle.ui.navigation.Destination

/** Light click sound (follows the phone's touch-sound setting) plus a soft haptic tick. */
@Composable
fun rememberTouchFeedback(): () -> Unit {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    val action: () -> Unit = remember(view, haptic) {
        {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }
    return action
}

@Composable
fun NexoraTopBar(title: String, onSettingsClick: () -> Unit) {
    val feedback = rememberTouchFeedback()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(52.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        PressableIcon(
            icon = Icons.Outlined.Settings,
            description = "Settings",
            onClick = {
                feedback()
                onSettingsClick()
            }
        )
    }
}

@Composable
private fun PressableIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "iconScale"
    )
    Box(
        modifier = Modifier
            .size(44.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .clickable(
                interactionSource = interaction,
                indication = null,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun NexoraBottomBar(currentRoute: String?, onSelect: (Destination) -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Destination.entries.forEach { d ->
                    BarItem(
                        destination = d,
                        selected = currentRoute == d.route,
                        onClick = { onSelect(d) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BarItem(
    destination: Destination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val feedback = rememberTouchFeedback()
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val primary = MaterialTheme.colorScheme.primary

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.88f else if (selected) 1.06f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "tabScale"
    )
    val pillAlpha by animateFloatAsState(
        targetValue = if (selected) 0.18f else 0f,
        label = "tabPill"
    )
    val tint by animateColorAsState(
        targetValue = if (selected) primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "tabTint"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interaction,
                indication = null,
                role = Role.Tab,
                onClick = {
                    feedback()
                    onClick()
                }
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primary.copy(alpha = pillAlpha)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (selected) destination.selectedIcon else destination.icon,
                contentDescription = destination.label,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = destination.label,
            fontSize = 10.sp,
            color = tint,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1
        )
    }
}
