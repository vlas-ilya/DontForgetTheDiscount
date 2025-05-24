package su.tease.project.feature.cashback.presentation.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.mapper.toCashBackDate
import su.tease.project.feature.cashback.domain.repository.BankAccountRepository
import su.tease.project.feature.cashback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cashback.presentation.list.action.LoadBankAccountListActions
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

class LoadBankAccountListActionImpl(
    private val repository: BankAccountRepository,
    private val dateProvider: DateProvider,
) : LoadBankAccountListAction {

    override fun run(request: CashBackDate?) = suspendAction {
        dispatch(LoadBankAccountListActions.OnLoad)
        try {
            val date = request ?: dateProvider.current().toCashBackDate()
            val list = repository.filterBy(date)
            val dates = repository.listDates().takeIf { it.isNotEmpty() } ?: persistentListOf(date)
            dispatch(LoadBankAccountListActions.OnSuccess(date, dates, list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadBankAccountListActions.OnFail)
        }
    }
}

private fun PersistentList<BankAccount>.sortBanks(): PersistentList<BankAccount> = this
    .map { it.copy(cashBacks = it.cashBacks.sortCashBacks()) }
    .sortedBy { it.bankPreset.name }
    .toPersistentList()

private fun PersistentList<CashBack>.sortCashBacks(): PersistentList<CashBack> = this
    .map { it.copy(cashBackPreset = it.cashBackPreset.withCodeSorted()) }
    .sortedBy { it.cashBackPreset.name }
    .toPersistentList()

private fun CashBackPreset.withCodeSorted() = this
    .copy(mccCodes = mccCodes.sortedBy { it.code }.toPersistentList())
