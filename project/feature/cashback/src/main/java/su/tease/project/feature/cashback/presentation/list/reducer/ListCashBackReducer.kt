package su.tease.project.feature.cashback.presentation.list.reducer

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
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.defaultCashBackDate
import su.tease.project.feature.cashback.presentation.list.action.LoadBankAccountListActions as LoadList
import su.tease.project.feature.cashback.presentation.list.reducer.CashBackInfoDialogAction as Dialog

class ListCashBackReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadList -> onLoadList(action)
        is Dialog -> onCashBackInfoDialog(action)
        else -> this
    }

    private fun S.onLoadList(action: LoadList) = when (action) {
        is LoadList.OnLoad -> copy(status = Loading)
        is LoadList.OnFail -> copy(status = Failed)
        is LoadList.OnDateSelect -> copy(date = action.date)
        is LoadList.OnSuccess -> copy(
            status = Success,
            date = action.currentDate,
            dates = action.dates,
            list = action.list,
        )
    }

    private fun S.onCashBackInfoDialog(action: Dialog) = when (action) {
        is Dialog.OnShow -> copy(shownCashBack = action.data)
        is Dialog.OnHide -> copy(shownCashBack = null)
    }
}

@Parcelize
data class ListCashBackState(
    val status: LoadingStatus = Init,
    val date: CashBackDate = defaultCashBackDate,
    val dates: PersistentList<CashBackDate> = persistentListOf(),
    val list: PersistentList<BankAccount> = persistentListOf(),
    val shownCashBack: Pair<BankAccount, CashBack>? = null,
) : State

typealias S = ListCashBackState

@Parcelize
sealed class CashBackInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<BankAccount, CashBack>?) : Dialog()
    data object OnHide : Dialog()
}
