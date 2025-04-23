package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme

@Composable
fun DFPlaceholder(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = Theme.fonts.placeholder.fontSize,
        fontStyle = Theme.fonts.placeholder.fontStyle,
        fontWeight = Theme.fonts.placeholder.fontWeight,
        fontFamily = Theme.fonts.placeholder.fontFamily,
        color = Theme.colors.placeholder
    )
}