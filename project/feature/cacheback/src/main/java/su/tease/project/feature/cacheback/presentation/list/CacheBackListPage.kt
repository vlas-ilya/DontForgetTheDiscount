package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListFailed
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListInit
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListLoading
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListSuccess

class CacheBackListPage<S : State>(
    store: Store<S>,
    private val loadBankListUseCase: LoadBankListUseCase,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    init {
        dispatch(loadBankListUseCase())
    }

    @Composable
    @Suppress("EmptyFunctionBlock")
    override fun Compose() {
        val status = select(CacheBackState::status).collectAsState(LoadingStatus.Init)
        val list = select(CacheBackState::list).collectAsState(persistentListOf())
        val error = select(CacheBackState::error).collectAsState(null)

        when (status.value) {
            LoadingStatus.Init -> CacheBackListInit()
            LoadingStatus.Loading -> CacheBackListLoading()
            LoadingStatus.Success -> CacheBackListSuccess(list, ::onRefresh)
            LoadingStatus.Failed -> CacheBackListFailed(::onRefresh)
        }
    }

    private fun onRefresh() = dispatch(loadBankListUseCase())

    @Parcelize
    data object Target : NavigationTarget.Page
}
