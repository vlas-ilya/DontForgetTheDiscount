package su.tease.project.feature.bank.presentation.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate

interface LoadBankAccountsAction : MviUseCase<CashBackDate?>

@Parcelize
sealed class LoadBankAccountsActions : PlainAction {

    data object OnLoad : LoadBankAccountsActions()
    data class OnDateSelect(val date: CashBackDate) : LoadBankAccountsActions()
    data object OnFail : LoadBankAccountsActions()
    data class OnSuccess(
        val currentDate: CashBackDate,
        val dates: PersistentList<CashBackDate>,
        val list: PersistentList<BankAccount>
    ) : LoadBankAccountsActions()
}
