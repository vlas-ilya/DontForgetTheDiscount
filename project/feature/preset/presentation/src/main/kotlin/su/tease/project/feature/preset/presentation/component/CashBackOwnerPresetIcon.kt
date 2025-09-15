package su.tease.project.feature.preset.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset

@Composable
@NonSkippableComposable
fun CashBackOwnerPresetIcon(
    cashBackOwnerPreset: CashBackOwnerPreset,
    modifier: Modifier = Modifier,
    size: CashBackOwnerPresetIconSize = CashBackOwnerPresetIconSize.DEFAULT,
    clip: Shape = CircleShape,
) = when (cashBackOwnerPreset) {
    is BankPreset -> BankPresetIcon(
        iconPreset = cashBackOwnerPreset.iconPreset,
        name = cashBackOwnerPreset.name,
        modifier = modifier,
        clip = clip,
        size = when (size) {
            CashBackOwnerPresetIconSize.DEFAULT -> BankPresetIconSize.DEFAULT
        },
    )

    is ShopPreset -> ShopPresetIcon(
        iconPreset = cashBackOwnerPreset.iconPreset,
        name = cashBackOwnerPreset.name,
        modifier = modifier,
        clip = clip,
        size = when (size) {
            CashBackOwnerPresetIconSize.DEFAULT -> ShopPresetIconSize.DEFAULT
        },
    )
}

enum class CashBackOwnerPresetIconSize {
    DEFAULT,
}
