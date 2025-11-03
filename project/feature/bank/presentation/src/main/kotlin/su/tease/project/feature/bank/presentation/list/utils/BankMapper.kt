package su.tease.project.feature.bank.presentation.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.presentation.action.SaveCacheBackAction
import su.tease.project.feature.bank.presentation.component.BankAccountsItem
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageAddMoreCashBackItem
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageCashBackItem

fun List<BankAccount>.toUi(
    date: CashBackDate,
    bankPresetIconView: BankPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
    saveCacheBackAction: SaveCacheBackAction,
): LazyListItems =
    flatMapPersistent {
        it.toUi(
            date,
            bankPresetIconView,
            cashBackPresetIconView,
            store,
            saveCacheBackAction
        )
    }

fun BankAccount.toUi(
    date: CashBackDate,
    bankPresetIconView: BankPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
    saveCacheBackAction: SaveCacheBackAction,
): LazyListItems = buildPersistentList {
    val bank = this@toUi
    add(BankAccountsItem(bank, bankPresetIconView, store))
    cashBacks.forEach { add(BankAccountsPageCashBackItem(bank, it, cashBackPresetIconView, store, saveCacheBackAction)) }
    add(BankAccountsPageAddMoreCashBackItem(date, bank, store, saveCacheBackAction))
}
