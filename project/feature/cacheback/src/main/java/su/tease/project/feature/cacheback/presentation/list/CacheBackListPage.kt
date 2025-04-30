package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.AppFloatingButton
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.AddCacheBackFeature
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListFailed
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListInit
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListLoading
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackListSuccess
import su.tease.project.design.icons.R as RIcons

class CacheBackListPage(
    store: Store<*>,
    private val loadBankListUseCase: LoadBankListUseCase,
) : BasePageComponent(store) {

    init {
        dispatch(loadBankListUseCase())
    }

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            appConfig {
                copy(
                    titleRes = R.string.cache_back_page_title,
                    floatingButton = AppFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = ::onAddCacheBack,
                    )
                )
            }
        }

        val status = selectAsState(CacheBackState::status, LoadingStatus.Init)
        val list = selectAsState(CacheBackState::list, persistentListOf())
        val error = selectAsState(CacheBackState::error)

        when (status.value) {
            LoadingStatus.Init -> CacheBackListInit()
            LoadingStatus.Loading -> CacheBackListLoading()
            LoadingStatus.Failed -> CacheBackListFailed(::onRefresh)
            LoadingStatus.Success -> CacheBackListSuccess(list = list)
        }
    }

    private fun onRefresh() = dispatch(loadBankListUseCase())
    private fun onAddCacheBack() = forward(AddCacheBackFeature())

    @Parcelize
    data object Target : NavigationTarget.Page
}
