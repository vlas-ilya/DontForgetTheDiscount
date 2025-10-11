package su.tease.project.feature.bank.presentation.info.list.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageBankItem

fun List<BankAccount>.toUi(
    bankPresetIconView: BankPresetIconView,
    store: Store<*>,
    onClick: (BankAccount) -> Unit
): LazyListItems = mapPersistent {
    BankAccountsPageBankItem(it, bankPresetIconView, store, onClick)
}
