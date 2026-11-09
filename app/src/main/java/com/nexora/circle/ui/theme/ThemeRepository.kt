package com.nexora.circle.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.themeDataStore by preferencesDataStore(name = "nexora_theme")

class ThemeRepository(private val context: Context) {

    private object Keys {
        val MODE = stringPreferencesKey("mode")
        val ACCENT = intPreferencesKey("accent")
        val CORNER = intPreferencesKey("corner")
        val FONT_SCALE = floatPreferencesKey("font_scale")
    }

    val settings: Flow<ThemeSettings> = context.themeDataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { p ->
            val defaults = ThemeSettings()
            ThemeSettings(
                mode = p[Keys.MODE]
                    ?.let { name -> runCatching { ThemeMode.valueOf(name) }.getOrNull() }
                    ?: defaults.mode,
                accentArgb = p[Keys.ACCENT] ?: defaults.accentArgb,
                cornerRadiusDp = p[Keys.CORNER] ?: defaults.cornerRadiusDp,
                fontScale = p[Keys.FONT_SCALE] ?: defaults.fontScale
            )
        }

    suspend fun save(s: ThemeSettings) {
        context.themeDataStore.edit { p ->
            p[Keys.MODE] = s.mode.name
            p[Keys.ACCENT] = s.accentArgb
            p[Keys.CORNER] = s.cornerRadiusDp
            p[Keys.FONT_SCALE] = s.fontScale
        }
    }
}
