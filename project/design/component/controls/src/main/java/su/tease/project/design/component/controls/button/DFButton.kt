package su.tease.project.design.component.controls.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText

@Composable
fun DFButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: DFButtonType = DFButtonType.ACCENT,
    shape: Shape = RoundedCornerShape(Theme.sizes.roundInfinity)
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Theme.sizes.size48),
        shape = shape,
        colors = ButtonColors(
            containerColor = type.background(),
            contentColor = type.text(),
            disabledContainerColor = type.background(),
            disabledContentColor = type.text(),
        )
    ) {
        DFText(
            text = label.uppercase(),
            color = type.text(),
            style = Theme.fonts.button,
        )
    }
}

enum class DFButtonType(
    val background: @Composable () -> Color,
    val text: @Composable () -> Color,
) {
    ACCENT(
        { Theme.colors.buttonBackground },
        { Theme.colors.buttonText },
    ),
    GRAY(
        { Theme.colors.inputBackground },
        { Theme.colors.inputText },
    ),
}
