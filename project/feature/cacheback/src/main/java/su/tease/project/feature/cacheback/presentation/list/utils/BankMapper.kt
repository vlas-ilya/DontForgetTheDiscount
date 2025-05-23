package su.tease.project.feature.cacheback.presentation.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.presentation.list.component.ListCacheBackAddMoreItem
import su.tease.project.feature.cacheback.presentation.list.component.ListCacheBackBankItem
import su.tease.project.feature.cacheback.presentation.list.component.ListCacheBackCacheBackItem

fun BankAccount.toUi(date: CacheBackDate, store: Store<*>): LazyListItems = buildPersistentList {
    val bank = this@toUi
    add(ListCacheBackBankItem(bank, store))
    cacheBacks.forEach { add(ListCacheBackCacheBackItem(bank, it, store)) }
    add(ListCacheBackAddMoreItem(date, bank, store))
}

fun List<BankAccount>.toUi(date: CacheBackDate, store: Store<*>): LazyListItems =
    flatMapPersistent { it.toUi(date, store) }
