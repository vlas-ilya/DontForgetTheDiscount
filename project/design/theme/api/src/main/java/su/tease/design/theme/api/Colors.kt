package su.tease.design.theme.api

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
interface Colors {
    val accent1: Color
    val accent2: Color
    val accent3: Color
    val accent4: Color
    val background: Color
    val header: Color
    val headerText: Color
    val headerAccentText: Color
    val text: Color
    val placeholder: Color
    val smallTitle: Color
    val shimmer1: Color
    val shimmer2: Color
    val navigationBarBackground: Color
    val navigationItemContent: Color
    val navigationItemContentSelected: Color
}
