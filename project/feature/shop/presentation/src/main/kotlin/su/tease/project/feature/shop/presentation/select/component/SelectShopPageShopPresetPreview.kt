package su.tease.project.feature.shop.presentation.select.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.shop.domain.entity.ShopPreset
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.Compose

@Composable
fun SelectShopPageShopPresetPreview(
    shopPreset: ShopPreset,
    shopPresetIconView: ShopPresetIconView,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .thenIfNotNull(onClick) { Modifier.clickable { it() } }
            .padding(horizontal = Theme.sizes.padding4)
            .padding(
                vertical = Theme.sizes.padding4,
                horizontal = Theme.sizes.padding4,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        shopPresetIconView.Compose(shopPreset.iconPreset, shopPreset.name)
        DFText(
            text = shopPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
