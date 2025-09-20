package su.tease.project.feature.shop.presentation.list.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.Compose

data class ShopsPageShopItem(
    private val shop: Shop,
    private val shopPresetIconView: ShopPresetIconView,
    private val store: Store<*>,
) : LazyListItem {

    override val key = SHOP + shop.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            modifier = Modifier.fillParentMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            shopPresetIconView.Compose(
                iconPreset = shop.preset.iconPreset,
                name = shop.customName,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding8),
            )

            DFText(
                text = shop.customName,
                style = Theme.fonts.placeholder,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
            )
        }
    }
}

private const val SHOP = "SHOP"
