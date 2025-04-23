package su.tease.project.design.component.controls.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import su.tease.design.theme.api.Theme

@Composable
fun DFTextH1(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Theme.colors.text,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = Theme.fonts.h1.fontSize,
        fontStyle = Theme.fonts.h1.fontStyle,
        fontWeight = Theme.fonts.h1.fontWeight,
        fontFamily = Theme.fonts.h1.fontFamily,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
    )
}
