package su.tease.project.design.theme.impl.utils

import su.tease.design.theme.api.Fonts
import su.tease.design.theme.api.utils.FontSetting
import su.tease.project.design.theme.impl.ThemeValue

internal data class FontsImpl(
    override val text: FontSetting
) : Fonts {

    companion object {
        internal fun make(themeValue: ThemeValue): Fonts = when (themeValue) {
            ThemeValue.LIGHT -> makeLightFonts()
            ThemeValue.NIGHT -> makeNightFonts()
        }
    }
}

private fun makeLightFonts(): Fonts = FontsImpl(

)

private fun makeNightFonts(): Fonts = FontsImpl(

)
