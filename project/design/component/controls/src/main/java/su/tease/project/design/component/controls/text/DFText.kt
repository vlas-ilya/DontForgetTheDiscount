package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Theme
import su.tease.design.theme.api.utils.FontSetting

@Composable
fun DFText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    style: FontSetting = Theme.fonts.text,
    color: Color = Theme.colors.text,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = style.fontSize,
        fontStyle = style.fontStyle,
        fontWeight = style.fontWeight,
        fontFamily = style.fontFamily,
        color = color,
        maxLines = maxLines,
    )
}
