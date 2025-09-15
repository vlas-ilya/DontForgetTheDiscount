package su.tease.project.feature.bank.presentation.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.entity.CashBackPreset
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsAction
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsActions
import su.tease.project.feature.bank.presentation.list.utils.toCashBackDate

class LoadBankAccountsActionImpl(
    private val interactor: BankAccountInterceptor,
    private val dateProvider: DateProvider,
) : LoadBankAccountsAction {

    override fun run(request: CashBackDate?) = suspendAction {
        dispatch(LoadBankAccountsActions.OnLoad)
        try {
            val date = request ?: dateProvider.current().toCashBackDate()
            val list = interactor.filterBy(date)
            val dates = interactor.listDates().takeIf { it.isNotEmpty() } ?: persistentListOf(date)
            dispatch(LoadBankAccountsActions.OnSuccess(date, dates, list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadBankAccountsActions.OnFail)
        }
    }
}

private fun List<BankAccount>.sortBanks(): PersistentList<BankAccount> = this
    .map { it.copy(cashBacks = it.cashBacks.sortCashBacks()) }
    .sortedBy { it.preset.name }
    .toPersistentList()

private fun List<CashBack>.sortCashBacks(): PersistentList<CashBack> = this
    .map { it.copy(preset = it.preset.withCodeSorted()) }
    .sortedBy { it.preset.name }
    .toPersistentList()

private fun CashBackPreset.withCodeSorted() = this
    .copy(mccCodes = mccCodes.sortedBy { it.code }.toPersistentList())
