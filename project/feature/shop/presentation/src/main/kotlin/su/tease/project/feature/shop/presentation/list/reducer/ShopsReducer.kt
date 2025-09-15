package su.tease.project.feature.shop.presentation.list.reducer

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
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.entity.defaultCashBackDate
import su.tease.project.feature.shop.presentation.list.action.LoadShopsActions

class ShopsReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadShopsActions -> onLoadList(action)
        is CashBackInfoDialogAction -> onCashBackInfoDialog(action)
        else -> this
    }

    private fun S.onLoadList(action: LoadShopsActions) = when (action) {
        is LoadShopsActions.OnLoad -> copy(status = Loading)
        is LoadShopsActions.OnFail -> copy(status = Failed)
        is LoadShopsActions.OnDateSelect -> copy(date = action.date)
        is LoadShopsActions.OnSuccess -> copy(
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
data class ShopsState(
    val status: LoadingStatus = Init,
    val date: CashBackDate = defaultCashBackDate,
    val dates: PersistentList<CashBackDate> = persistentListOf(),
    val list: PersistentList<Shop> = persistentListOf(),
    val shownCashBack: Pair<Shop, CashBack>? = null,
) : State

typealias S = ShopsState

@Parcelize
sealed class CashBackInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<Shop, CashBack>?) : CashBackInfoDialogAction()
    data object OnHide : CashBackInfoDialogAction()
}
