package su.tease.project.feature.bank.presentation.dependencies.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import su.tease.project.feature.bank.domain.entity.BankIconPreset

interface BankPresetIconView {
    @Composable
    fun ComposeComponent(
        iconPreset: BankIconPreset,
        name: String,
        modifier: Modifier,
        clip: Shape,
    )
}

@Composable
fun BankPresetIconView.Compose(
    iconPreset: BankIconPreset,
    name: String,
    modifier: Modifier = Modifier,
    clip: Shape = CircleShape,
) = ComposeComponent(
    iconPreset = iconPreset,
    name = name,
    modifier = modifier,
    clip = clip,
)
