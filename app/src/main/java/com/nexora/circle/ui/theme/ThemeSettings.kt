package com.nexora.circle.ui.theme

enum class ThemeMode(val label: String) {
    LIGHT("Light"),
    DARK("Dark"),
    OLED("OLED Black")
}

data class AccentPreset(val name: String, val argb: Int)

object AccentPresets {
    val Pink = AccentPreset("Pink", 0xFFFF3EB5.toInt())
    val Purple = AccentPreset("Purple", 0xFF9B6BFF.toInt())
    val Blue = AccentPreset("Blue", 0xFF3E8BFF.toInt())
    val Green = AccentPreset("Green", 0xFF2ECC8F.toInt())
    val all = listOf(Pink, Purple, Blue, Green)
}

data class ThemeSettings(
    val mode: ThemeMode = ThemeMode.DARK,
    val accentArgb: Int = AccentPresets.Pink.argb,
    val cornerRadiusDp: Int = 20,
    val fontScale: Float = 1.0f
)

fun argbToHex(argb: Int): String = "%06X".format(argb and 0xFFFFFF)
