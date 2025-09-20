package su.tease.project.feature.shop.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import su.tease.project.feature.shop.domain.entity.ShopIconPreset
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.preset.presentation.component.ShopPresetIconSize
import su.tease.project.feature.preset.domain.entity.ShopIconPreset as PresetShopIconPreset
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon as PresetShopPresetIcon

class ShopPresetIconViewImpl : ShopPresetIconView {

    @Composable
    override fun ComposeComponent(
        iconPreset: ShopIconPreset,
        name: String,
        modifier: Modifier,
        clip: Shape
    ) {
        PresetShopPresetIcon(
            iconPreset = iconPreset.toPreset(),
            name = name,
            size = ShopPresetIconSize.DEFAULT,
            modifier = modifier,
            clip = clip,
        )
    }

    private fun ShopIconPreset.toPreset() = PresetShopIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
