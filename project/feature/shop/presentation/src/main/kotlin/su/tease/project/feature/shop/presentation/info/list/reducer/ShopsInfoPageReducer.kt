package su.tease.project.feature.shop.presentation.info.list.reducer

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
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.info.list.action.LoadShopsInfoActions

class ShopsInfoPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadShopsInfoActions -> onLoadList(action)
        else -> this
    }

    fun S.onLoadList(action: LoadShopsInfoActions): S = when (action) {
        is LoadShopsInfoActions.OnLoad -> copy(status = Loading)
        is LoadShopsInfoActions.OnFail -> copy(status = Failed)
        is LoadShopsInfoActions.OnSuccess -> copy(status = Success, list = action.list)
    }
}

@Parcelize
data class ShopsInfoPageState(
    val status: LoadingStatus = Init,
    val list: PersistentList<Shop> = persistentListOf(),
) : State

typealias S = ShopsInfoPageState
