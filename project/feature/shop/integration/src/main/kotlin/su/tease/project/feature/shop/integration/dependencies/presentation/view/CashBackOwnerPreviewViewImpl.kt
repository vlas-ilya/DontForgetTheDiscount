package su.tease.project.feature.shop.integration.dependencies.presentation.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.preset.presentation.component.ShopPresetIconSize
import su.tease.project.feature.shop.integration.mapper.preset.toPreset
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon as PresetShopPresetIcon

class CashBackOwnerPreviewViewImpl : CashBackOwnerPreviewView {

    @Composable
    override fun ComposeIconComponent(
        name: String,
        icon: CashBackOwnerIconPreset,
        modifier: Modifier,
    ) {
        PresetShopPresetIcon(
            iconPreset = icon.toPreset(),
            name = name,
            size = ShopPresetIconSize.DEFAULT,
            clip = CircleShape,
            modifier = modifier,
        )
    }
}
