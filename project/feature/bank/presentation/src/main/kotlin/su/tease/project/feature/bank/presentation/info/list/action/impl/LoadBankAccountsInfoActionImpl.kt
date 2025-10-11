package su.tease.project.feature.bank.presentation.info.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoAction
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoActions

class LoadBankAccountsInfoActionImpl(
    private val interactor: BankAccountInterceptor,
) : LoadBankAccountsInfoAction {

    override fun run() = suspendAction {
        dispatch(LoadBankAccountsInfoActions.OnLoad)
        val list = interactor.listWithoutCashbacks()
        try {
            dispatch(LoadBankAccountsInfoActions.OnSuccess(list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadBankAccountsInfoActions.OnFail)
        }
    }
}

private fun List<BankAccount>.sortBanks(): PersistentList<BankAccount> = this
    .sortedBy { it.customName }
    .toPersistentList()
