package su.tease.project.feature.cacheback.presentation.list.mapper

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.presentation.list.component.BankAddMoreItem
import su.tease.project.feature.cacheback.presentation.list.component.BankItem
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackItem

fun Bank.toUi(store: Store<*>): LazyListItems = buildPersistentList {
    val bank = this@toUi
    add(BankItem(bank, store))
    cacheBacks.forEach { add(CacheBackItem(it, store)) }
    add(BankAddMoreItem(bank, store))
}

fun List<Bank>.toUi(store: Store<*>): LazyListItems =
    flatMapPersistent { it.toUi(store) }
