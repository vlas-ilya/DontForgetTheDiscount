package su.tease.design.theme.api

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
interface Colors {
    val background: Color
    val text: Color
    val navigationBarBackground: Color
    val navigationItemContent: Color
    val navigationItemContentSelected: Color
}
