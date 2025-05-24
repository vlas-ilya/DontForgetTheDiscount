package su.tease.project.feature.cashback.presentation.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.flatMapPersistent
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.presentation.list.component.ListCashBackAddMoreItem
import su.tease.project.feature.cashback.presentation.list.component.ListCashBackBankItem
import su.tease.project.feature.cashback.presentation.list.component.ListCashBackCashBackItem

fun BankAccount.toUi(date: CashBackDate, store: Store<*>): LazyListItems = buildPersistentList {
    val bank = this@toUi
    add(ListCashBackBankItem(bank, store))
    cashBacks.forEach { add(ListCashBackCashBackItem(bank, it, store)) }
    add(ListCashBackAddMoreItem(date, bank, store))
}

fun List<BankAccount>.toUi(date: CashBackDate, store: Store<*>): LazyListItems =
    flatMapPersistent { it.toUi(date, store) }
