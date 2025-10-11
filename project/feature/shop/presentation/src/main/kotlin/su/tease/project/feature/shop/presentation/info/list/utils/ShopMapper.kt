package su.tease.project.feature.shop.presentation.info.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.list.component.ShopsPageShopItem

fun List<Shop>.toUi(
    shopPresetIconView: ShopPresetIconView,
    store: Store<*>,
    onClick: (Shop) -> Unit
): LazyListItems = mapPersistent {
    ShopsPageShopItem(it, shopPresetIconView, store, onClick)
}
