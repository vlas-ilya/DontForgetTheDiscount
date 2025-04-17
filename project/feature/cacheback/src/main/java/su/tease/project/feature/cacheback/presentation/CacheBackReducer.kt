package su.tease.project.feature.cacheback.presentation

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListError

@Parcelize
data class CacheBackState(
    val status: LoadingStatus = LoadingStatus.Init,
    val list: PersistentList<CacheBackBank> = persistentListOf(),
    val error: LoadCacheBackBankListError? = null,
) : State

class CacheBackReducer : Reducer<CacheBackState> {

    override val initState = CacheBackState()

    override fun CacheBackState.onAction(action: PlainAction) = when (action) {
        is LoadCacheBackBankListAction -> onLoadCacheBackBankListAction(action)
        else -> this
    }

    private fun CacheBackState.onLoadCacheBackBankListAction(
        action: LoadCacheBackBankListAction
    ) = when (action) {
        is LoadCacheBackBankListAction.Loading -> copy(
            status = LoadingStatus.Loading,
            list = persistentListOf(),
            error = null,
        )

        is LoadCacheBackBankListAction.Success -> copy(
            status = LoadingStatus.Success,
            list = action.list,
            error = null,
        )

        is LoadCacheBackBankListAction.Failed -> copy(
            status = LoadingStatus.Failed,
            error = action.error,
        )
    }
}
