package su.tease.project.feature.bank.presentation.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.bank.presentation.list.component.ListCashBackAddMoreItem
import su.tease.project.feature.bank.presentation.list.component.ListCashBackBankItem
import su.tease.project.feature.bank.presentation.list.component.ListCashBackCashBackItem

fun List<BankAccount>.toUi(
    date: CashBackDate,
    bankPresetIconView: BankPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
): LazyListItems = flatMapPersistent { it.toUi(date, bankPresetIconView, cashBackPresetIconView, store) }

fun BankAccount.toUi(
    date: CashBackDate,
    bankPresetIconView: BankPresetIconView,
    cashBackPresetIconView: CashBackPresetIconView,
    store: Store<*>,
): LazyListItems = buildPersistentList {
    val bank = this@toUi
    add(ListCashBackBankItem(bank, bankPresetIconView, store))
    cashBacks.forEach { add(ListCashBackCashBackItem(bank, it, cashBackPresetIconView, store)) }
    add(ListCashBackAddMoreItem(date, bank, store))
}
