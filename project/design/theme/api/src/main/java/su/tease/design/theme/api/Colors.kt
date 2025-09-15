package su.tease.design.theme.api

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
interface Colors {
    val accent: Color
    val background1: Color
    val background2: Color
    val headerText: Color
    val text: Color
    val placeholder: Color
    val info: Color
    val error: Color
    val transparent: Color
    val iconTint: Color
    val iTint: Color

    val inputBorder: Color
    val inputFocusedBorder: Color
    val inputBackground: Color
    val inputText: Color
    val inputPlaceholder: Color

    val buttonBackground: Color
    val buttonText: Color

    val notificationSuccessBackground: Color
    val notificationSuccessText: Color

    val notificationInfoBackground: Color
    val notificationInfoText: Color

    val notificationErrorBackground: Color
    val notificationErrorText: Color

    val link: Color

    val shimmer: Color

    val tmpFiller: Color
}
