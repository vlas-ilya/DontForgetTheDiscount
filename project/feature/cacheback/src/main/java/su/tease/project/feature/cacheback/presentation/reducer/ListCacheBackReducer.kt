package su.tease.project.feature.cacheback.presentation.reducer

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
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction as LoadList

@Parcelize
data class ListCacheBackState(
    val status: LoadingStatus = Init,
    val date: CacheBackDate = defaultCacheBackDate,
    val dates: PersistentList<CacheBackDate> = persistentListOf(),
    val list: PersistentList<Bank> = persistentListOf(),
    val error: Boolean = false,
) : State

class ListCacheBackReducer : Reducer<ListCacheBackState> {

    override val initState = ListCacheBackState()

    override fun ListCacheBackState.onAction(action: PlainAction) = when (action) {
        is LoadList -> onLoadList(action)
        else -> this
    }

    private fun ListCacheBackState.onLoadList(action: LoadList) =
        when (action) {
            is LoadList.OnLoad -> copy(status = Loading, error = false)
            is LoadList.OnFail -> copy(status = Failed, error = true)
            is LoadList.OnDateSelect -> copy(date = action.date)
            is LoadList.OnSuccess -> copy(
                status = Success,
                date = action.date,
                dates = action.dates,
                list = action.list,
                error = false
            )
        }
}
