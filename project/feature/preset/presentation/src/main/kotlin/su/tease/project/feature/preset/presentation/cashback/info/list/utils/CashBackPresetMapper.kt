package su.tease.project.feature.preset.presentation.cashback.info.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.presentation.cashback.info.list.component.CashBackPresetPreview

fun List<CashBackPreset>.toUi(
    store: Store<*>,
    onClick: ((CashBackPreset) -> Unit)?
): LazyListItems = mapPersistent { it.toUi(store, onClick) }

fun CashBackPreset.toUi(
    store: Store<*>,
    onClick: ((CashBackPreset) -> Unit)?
): LazyListItem = CashBackPresetPreview(this, store, onClick)
