package su.tease.project.design.theme.impl.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import su.tease.design.theme.api.Fonts
import su.tease.design.theme.api.utils.FontSetting
import su.tease.project.design.theme.impl.ThemeValue

internal data class FontsImpl(
    override val text: FontSetting,
) : Fonts {

    companion object {
        internal fun make(themeValue: ThemeValue): Fonts = when (themeValue) {
            ThemeValue.LIGHT -> makeLightFonts()
            ThemeValue.NIGHT -> makeNightFonts()
        }
    }
}

private fun makeLightFonts(): Fonts = FontsImpl(
    text = FontSetting(
        fontSize = 16.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default,
    ),
)

private fun makeNightFonts(): Fonts = makeLightFonts()
