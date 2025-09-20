package su.tease.project.feature.shop.presentation.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.list.component.ShopsAddMoreCashBackItem
import su.tease.project.feature.shop.presentation.list.component.ShopsPageCashBackItem
import su.tease.project.feature.shop.presentation.list.component.ShopsPageShopItem

fun List<Shop>.toUi(
    date: CashBackDate,
    shopPresetIconView: ShopPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
): LazyListItems = flatMapPersistent { it.toUi(date, shopPresetIconView, cashBackPresetIconView, store) }

fun Shop.toUi(
    date: CashBackDate,
    shopPresetIconView: ShopPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
): LazyListItems = buildPersistentList {
    val shop = this@toUi
    add(ShopsPageShopItem(shop, shopPresetIconView, store))
    cashBacks.forEach { add(ShopsPageCashBackItem(shop, it, cashBackPresetIconView, store)) }
    add(ShopsAddMoreCashBackItem(date, shop, store))
}
