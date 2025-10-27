package su.tease.project.feature.preset.presentation.bank.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.bank.component.BankPresetPreview

fun List<BankPreset>.toUi(
    store: Store<*>,
    onClick: ((BankPreset) -> Unit)?
): LazyListItems = mapPersistent { it.toUi(store, onClick) }

fun BankPreset.toUi(
    store: Store<*>,
    onClick: ((BankPreset) -> Unit)?
): LazyListItem = BankPresetPreview(this, store, onClick)
