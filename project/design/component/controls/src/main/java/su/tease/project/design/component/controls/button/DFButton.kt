package su.tease.project.design.component.controls.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText

@Composable
fun DFButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Theme.sizes.size48),
        colors = ButtonColors(
            containerColor = Theme.colors.buttonBackground,
            contentColor = Theme.colors.buttonText,
            disabledContainerColor = Theme.colors.buttonBackground,
            disabledContentColor = Theme.colors.buttonText,
        )
    ) {
        DFText(
            text = label.uppercase(),
            color = Theme.colors.buttonText,
            style = Theme.fonts.button,
        )
    }
}
