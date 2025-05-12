package su.tease.project.feature.cacheback.presentation.list.reducer

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
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListActions as LoadList
import su.tease.project.feature.cacheback.presentation.list.reducer.CacheBackInfoDialogAction as Dialog

@Parcelize
data class ListCacheBackState(
    val status: LoadingStatus = Init,
    val date: CacheBackDate = defaultCacheBackDate,
    val dates: PersistentList<CacheBackDate> = persistentListOf(),
    val list: PersistentList<BankAccount> = persistentListOf(),
    val shownCacheBack: Pair<BankAccount, CacheBack>? = null,
    val error: Boolean = false,
) : State

typealias S = ListCacheBackState

class ListCacheBackReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadList -> onLoadList(action)
        is Dialog -> anCacheBackInfoDialog(action)
        else -> this
    }

    private fun S.onLoadList(action: LoadList) = when (action) {
        is LoadList.OnLoad -> copy(status = Loading, error = false)
        is LoadList.OnFail -> copy(status = Failed, error = true)
        is LoadList.OnDateSelect -> copy(date = action.date)
        is LoadList.OnSuccess -> copy(
            status = Success,
            date = action.currentDate,
            dates = action.dates,
            list = action.list,
            error = false
        )
    }

    private fun S.anCacheBackInfoDialog(action: Dialog) = when (action) {
        is Dialog.OnShow -> copy(shownCacheBack = action.data)
        is Dialog.OnHide -> copy(shownCacheBack = null)
    }
}

@Parcelize
sealed class CacheBackInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<BankAccount, CacheBack>?) : Dialog()
    data object OnHide : Dialog()
}
