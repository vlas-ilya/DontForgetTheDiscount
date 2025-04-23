package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Theme

@Composable
fun DFText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Theme.colors.text,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = Theme.fonts.text.fontSize,
        fontStyle = Theme.fonts.text.fontStyle,
        fontWeight = Theme.fonts.text.fontWeight,
        fontFamily = Theme.fonts.text.fontFamily,
        color = color,
        maxLines = maxLines,
    )
}
