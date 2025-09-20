package su.tease.project.feature.bank.presentation.list.reducer

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.entity.defaultCashBackDate
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsActions

class BankAccountsPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadBankAccountsActions -> onLoadList(action)
        is CashBackInfoDialogAction -> onCashBackInfoDialog(action)
        else -> this
    }

    private fun S.onLoadList(action: LoadBankAccountsActions) = when (action) {
        is LoadBankAccountsActions.OnLoad -> copy(status = Loading)
        is LoadBankAccountsActions.OnFail -> copy(status = Failed)
        is LoadBankAccountsActions.OnDateSelect -> copy(date = action.date)
        is LoadBankAccountsActions.OnSuccess -> copy(
            status = Success,
            date = action.currentDate,
            dates = action.dates,
            list = action.list,
        )
    }

    private fun S.onCashBackInfoDialog(action: CashBackInfoDialogAction) = when (action) {
        is CashBackInfoDialogAction.OnShow -> copy(shownCashBack = action.data)
        is CashBackInfoDialogAction.OnHide -> copy(shownCashBack = null)
    }
}

@Parcelize
data class BankAccountsState(
    val status: LoadingStatus = Init,
    val date: CashBackDate = defaultCashBackDate,
    val dates: PersistentList<CashBackDate> = persistentListOf(),
    val list: PersistentList<BankAccount> = persistentListOf(),
    val shownCashBack: Pair<BankAccount, CashBack>? = null,
) : State

typealias S = BankAccountsState

@Parcelize
sealed class CashBackInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<BankAccount, CashBack>?) : CashBackInfoDialogAction()
    data object OnHide : CashBackInfoDialogAction()
}
