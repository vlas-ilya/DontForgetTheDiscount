package su.tease.project.feature.preset.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.presentation.R

@Composable
fun CashBackPresetIcon(
    name: String,
    iconPreset: CashBackIconPreset,
    modifier: Modifier = Modifier,
    size: CashBackPresetIconSize = CashBackPresetIconSize.DEFAULT,
    background: Color = Theme.colors.inputBackground,
) {
    DFImage(
        url = iconPreset.iconUrl,
        contentDescription = stringResource(
            R.string.Presets_CashBackPresetIconItem_ContentDescription,
            name,
        ),
        modifier = modifier
            .clip(CircleShape)
            .background(background)
            .thenIf(iconPreset.iconUrl.endsWith(".png")) {
                Modifier
                    .size(size.size() + size.padding())
            }
            .thenIf(!iconPreset.iconUrl.endsWith(".png")) {
                Modifier
                    .padding(size.padding())
                    .size(size.size())
            }
    )
}

enum class CashBackPresetIconSize(
    val size: @Composable () -> Dp,
    val padding: @Composable () -> Dp,
) {
    EXTRA_SMALL(
        size = { Theme.sizes.size22 },
        padding = { Theme.sizes.padding6 },
    ),
    SMALL(
        size = { Theme.sizes.size24 },
        padding = { Theme.sizes.padding8 },
    ),
    DEFAULT(
        size = { Theme.sizes.size32 },
        padding = { Theme.sizes.padding14 },
    ),
}
