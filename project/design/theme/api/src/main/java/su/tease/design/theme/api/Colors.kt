package su.tease.design.theme.api

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
interface Colors {
    val accent: Color
    val background0: Color
    val background1: Color
    val headerText: Color
    val text: Color
    val placeholder: Color
    val info: Color
    val error: Color
    val transparent: Color
    val iconTint: Color

    val inputBorder: Color
    val inputFocusedBorder: Color
    val inputBackground: Color
    val inputText: Color
    val inputPlaceholder: Color

    val tmpFiller: Color
}
