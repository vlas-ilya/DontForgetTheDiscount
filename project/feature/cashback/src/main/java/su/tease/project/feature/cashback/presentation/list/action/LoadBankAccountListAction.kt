package su.tease.project.feature.cashback.presentation.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate

interface LoadBankAccountListAction : MviUseCase<CashBackDate?>

@Parcelize
sealed class LoadBankAccountListActions : PlainAction {

    data object OnLoad : LoadBankAccountListActions()
    data class OnDateSelect(val date: CashBackDate) : LoadBankAccountListActions()
    data object OnFail : LoadBankAccountListActions()
    data class OnSuccess(
        val currentDate: CashBackDate,
        val dates: PersistentList<CashBackDate>,
        val list: PersistentList<BankAccount>,
    ) : LoadBankAccountListActions()
}
