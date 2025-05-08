@file:Suppress("MagicNumber", "UnusedPrivateProperty")

package su.tease.project.design.theme.impl.utils

import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Colors
import su.tease.project.design.theme.impl.ThemeValue

internal data class ColorsImpl(
    override val accent: Color,
    override val background0: Color,
    override val background1: Color,
    override val headerText: Color,
    override val text: Color,
    override val placeholder: Color,
    override val info: Color,
    override val error: Color,
    override val transparent: Color,
    override val iconTint: Color,

    override val buttonBackground: Color,
    override val buttonText: Color,

    override val inputBorder: Color,
    override val inputFocusedBorder: Color,
    override val inputBackground: Color,
    override val inputText: Color,
    override val inputPlaceholder: Color,

    override val link: Color,

    override val notificationSuccessBackground: Color,
    override val notificationSuccessText: Color,

    override val notificationInfoBackground: Color,
    override val notificationInfoText: Color,

    override val notificationErrorBackground: Color,
    override val notificationErrorText: Color,

    override val tmpFiller: Color,
) : Colors {
    companion object {
        internal fun make(themeValue: ThemeValue): Colors = when (themeValue) {
            ThemeValue.LIGHT -> makeLightColors()
            ThemeValue.NIGHT -> makeNightColors()
        }
    }
}

private val accent1 = Color(0xFFEF5A6F)
private val accent2 = Color(0xFFFFF1DB)
private val accent3 = Color(0xFFD4BDAC)
private val accent4 = Color(0xFF536493)

private val white = Color(0xFFFFFFFF)
private val black = Color(0xFF000000)
private val text = Color(0xFF232942)
private val transparent = Color(0x00FFFFFF)

private fun makeLightColors(): Colors = ColorsImpl(
    accent = accent1,
    background0 = accent1,
    background1 = Color(0xFFFFF5FA),
    headerText = Color(0xFFFFF5FA),
    text = text,
    placeholder = Color(0xFFBBBBBB),
    info = Color(0xFF6C8FF6),
    error = Color(0xFFD22038),
    transparent = Color.Transparent,
    iconTint = accent4,

    buttonBackground = accent4,
    buttonText = Color(0xFFFFF5FA),

    inputBorder = Color.Transparent,
    inputFocusedBorder = accent1,
    inputBackground = Color(0xFFEEEEEE),
    inputText = text,
    inputPlaceholder = Color(0xFFBBBBBB),

    link = accent4,

    notificationSuccessBackground = accent4,
    notificationSuccessText = Color(0xFFFFF5FA),

    notificationInfoBackground = accent3,
    notificationInfoText = accent4,

    notificationErrorBackground = Color(0xFFD22038),
    notificationErrorText = Color(0xFFFFF5FA),

    tmpFiller = black,
)

private fun makeNightColors(): Colors = makeLightColors()
