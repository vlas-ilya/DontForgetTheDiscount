package su.tease.project.design.theme.impl.utils

import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Colors
import su.tease.project.design.theme.impl.ThemeValue
import androidx.compose.ui.graphics.Color as ComposeColor

internal data class ColorsImpl(
    override val background: ComposeColor,
    override val text: Color,
) : Colors {

    companion object {
        internal fun make(themeValue: ThemeValue): Colors = when (themeValue) {
            ThemeValue.LIGHT -> makeLightColors()
            ThemeValue.NIGHT -> makeNightColors()
        }
    }
}

private fun makeLightColors(): Colors = ColorsImpl(
    background = Color.White,
    text = Color.Black,
)

private fun makeNightColors(): Colors = ColorsImpl(
    background = Color.Black,
    text = Color.White,
)
