package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import su.tease.design.theme.api.Theme

@Composable
fun DFPlaceholder(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.inputPlaceholder
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = Theme.fonts.placeholder.fontSize,
        fontStyle = Theme.fonts.placeholder.fontStyle,
        fontWeight = Theme.fonts.placeholder.fontWeight,
        fontFamily = Theme.fonts.placeholder.fontFamily,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = color,
    )
}
