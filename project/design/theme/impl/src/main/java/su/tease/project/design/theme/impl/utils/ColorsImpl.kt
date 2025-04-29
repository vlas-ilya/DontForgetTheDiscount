@file:Suppress("MagicNumber")

package su.tease.project.design.theme.impl.utils

import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Colors
import su.tease.project.design.theme.impl.ThemeValue

internal data class ColorsImpl(
    override val accent1: Color,
    override val accent2: Color,
    override val accent3: Color,
    override val accent4: Color,
    override val header: Color,
    override val headerText: Color,
    override val headerAccentText: Color,
    override val background: Color,
    override val text: Color,
    override val placeholder: Color,
    override val smallTitle: Color,
    override val shimmer1: Color,
    override val shimmer2: Color,
    override val info: Color,
    override val error: Color,

    override val navigationBarBackground: Color,
    override val navigationItemContent: Color,
    override val navigationItemContentSelected: Color,
) : Colors {
    companion object {
        internal fun make(themeValue: ThemeValue): Colors = when (themeValue) {
            ThemeValue.LIGHT -> makeLightColors()
            ThemeValue.NIGHT -> makeNightColors()
        }
    }
}

private fun makeLightColors(): Colors = ColorsImpl(
    accent1 = Color(0xFFEF5A6F),
    accent2 = Color(0xFFFFF1DB),
    accent3 = Color(0xFFD4BDAC),
    accent4 = Color(0xFF536493),
    header = Color(0xFF536493),
    headerText = Color(0xFFFFF1DB),
    headerAccentText = Color(0xFFFFFFFF),
    background = Color(0xFFFFFCFA),
    text = Color(0xFF0A0F23),
    placeholder = Color(0xFFD4BDAC),
    smallTitle = Color(0xFF536493),
    shimmer1 = Color(0xFFFFF1DB),
    shimmer2 = Color(0xFFD4BDAC),
    info = Color(0xFF3D58A9),
    error = Color(0xFFD22038),

    navigationBarBackground = Color.DarkGray,
    navigationItemContent = Color.LightGray,
    navigationItemContentSelected = Color.White,
)

private fun makeNightColors(): Colors = makeLightColors()
