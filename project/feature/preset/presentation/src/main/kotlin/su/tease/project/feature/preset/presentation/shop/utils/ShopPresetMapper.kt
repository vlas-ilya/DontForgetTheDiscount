package su.tease.project.feature.preset.presentation.shop.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.shop.component.ShopPresetPreview

fun List<ShopPreset>.toUi(
    store: Store<*>,
    onClick: ((ShopPreset) -> Unit)?
): LazyListItems = mapPersistent { it.toUi(store, onClick) }

fun ShopPreset.toUi(
    store: Store<*>,
    onClick: ((ShopPreset) -> Unit)?
): LazyListItem = ShopPresetPreview(this, store, onClick)
