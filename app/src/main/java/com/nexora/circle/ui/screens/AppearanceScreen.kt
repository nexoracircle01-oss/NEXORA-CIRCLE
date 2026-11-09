package com.nexora.circle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nexora.circle.ui.theme.AccentPresets
import com.nexora.circle.ui.theme.NexoraTheme
import com.nexora.circle.ui.theme.ThemeMode
import com.nexora.circle.ui.theme.ThemeSettings
import com.nexora.circle.ui.theme.argbToHex
import kotlin.math.roundToInt

@Composable
fun AppearanceScreen(
    current: ThemeSettings,
    onSave: (ThemeSettings) -> Unit,
    onBack: () -> Unit
) {
    var draft by remember(current) { mutableStateOf(current) }
    var hex by remember(current) { mutableStateOf(argbToHex(current.accentArgb)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
            }
            Text("Appearance & Theme", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(12.dp))

        ThemePreview(draft)

        SectionTitle("Mode")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeMode.entries.forEach { m ->
                FilterChip(
                    selected = draft.mode == m,
                    onClick = { draft = draft.copy(mode = m) },
                    label = { Text(m.label) }
                )
            }
        }

        SectionTitle("Accent color")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AccentPresets.all.forEach { p ->
                AccentDot(
                    name = p.name,
                    color = Color(p.argb),
                    selected = draft.accentArgb == p.argb
                ) {
                    draft = draft.copy(accentArgb = p.argb)
                    hex = argbToHex(p.argb)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = hex,
            onValueChange = { input ->
                hex = input.uppercase().filter { it in "0123456789ABCDEF" }.take(6)
                if (hex.length == 6) {
                    draft = draft.copy(accentArgb = 0xFF000000.toInt() or hex.toInt(16))
                }
            },
            label = { Text("Custom accent (HEX)") },
            prefix = { Text("#") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        SectionTitle("Card roundness: ${draft.cornerRadiusDp} dp")
        Slider(
            value = draft.cornerRadiusDp.toFloat(),
            onValueChange = { draft = draft.copy(cornerRadiusDp = it.roundToInt()) },
            valueRange = 8f..32f
        )

        SectionTitle("Text size: ${(draft.fontScale * 100).roundToInt()}%")
        Slider(
            value = draft.fontScale,
            onValueChange = { draft = draft.copy(fontScale = (it * 20).roundToInt() / 20f) },
            valueRange = 0.85f..1.3f
        )

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = {
                draft = ThemeSettings()
                hex = argbToHex(draft.accentArgb)
            }) { Text("Reset") }
            Button(
                onClick = { onSave(draft) },
                enabled = draft != current
            ) { Text("Save theme") }
        }
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun AccentDot(name: String, color: Color, selected: Boolean, onClick: () -> Unit) {
    Spacer(
        Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                else Modifier
            )
            .clickable(onClick = onClick)
            .semantics { contentDescription = name }
    )
}

@Composable
private fun ThemePreview(settings: ThemeSettings) {
    NexoraTheme(settings) {
        val c = MaterialTheme.colorScheme
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(c.background)
                .border(1.dp, c.outline.copy(alpha = 0.5f), MaterialTheme.shapes.large)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Preview", style = MaterialTheme.typography.labelMedium, color = c.onSurfaceVariant)
            Card(
                colors = CardDefaults.cardColors(containerColor = c.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Orbit of curious minds", style = MaterialTheme.typography.titleMedium, color = c.onSurface)
                    Text(
                        "A calm space for ideas, science and creativity.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = c.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {}) { Text("Send Signal") }
                        OutlinedButton(onClick = {}) { Text("Join") }
                    }
                }
            }
        }
    }
}
