package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme

@Composable
fun DFSmallTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = Theme.fonts.smallTitle.fontSize,
        fontStyle = Theme.fonts.smallTitle.fontStyle,
        fontWeight = Theme.fonts.smallTitle.fontWeight,
        fontFamily = Theme.fonts.smallTitle.fontFamily,
        color = Theme.colors.text
    )
}
