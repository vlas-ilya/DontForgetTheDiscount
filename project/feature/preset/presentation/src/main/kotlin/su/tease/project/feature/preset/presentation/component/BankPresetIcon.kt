package su.tease.project.feature.preset.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.presentation.R

@Composable
fun BankPresetIcon(
    iconPreset: BankIconPreset,
    name: String,
    modifier: Modifier = Modifier,
    size: BankPresetIconSize = BankPresetIconSize.DEFAULT,
    clip: Shape = CircleShape,
) {
    DFImage(
        url = iconPreset.iconUrl,
        modifier = modifier
            .clip(clip)
            .size(size.size()),
        contentDescription = stringResource(
            R.string.Preset_BankIconPresetItem_ContentDescription,
            name,
        )
    )
}

enum class BankPresetIconSize(
    val size: @Composable () -> Dp
) {
    DEFAULT({ Theme.sizes.size32 }),
}
