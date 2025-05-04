package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.AppFloatingButton
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.AddCacheBackFeature
import su.tease.project.feature.cacheback.presentation.list.mapper.toUi
import su.tease.project.feature.cacheback.presentation.list.page.CacheBackListFailed
import su.tease.project.feature.cacheback.presentation.list.page.CacheBackListInit
import su.tease.project.feature.cacheback.presentation.list.page.CacheBackListLoading
import su.tease.project.feature.cacheback.presentation.list.page.CacheBackListSuccess
import su.tease.project.design.icons.R as RIcons

class CacheBackListPage(
    store: Store<*>,
    private val loadBankListUseCase: LoadBankListUseCase,
) : BasePageComponent(store) {

    private val lazyListState = LazyListState(
        firstVisibleItemIndex = 0,
        firstVisibleItemScrollOffset = 0,
    )

    @Composable
    override operator fun invoke() {
        val scope = rememberCoroutineScope()

        val status = selectAsState(CacheBackState::status, LoadingStatus.Init)

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success &&
                    lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
            }
        }

        val isPeriodSelectorVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success &&
                    lazyListState.firstVisibleItemIndex == 0
            }
        }

        LaunchedEffect(Unit) {
            dispatch(loadBankListUseCase())
        }

        LaunchedEffect(isScrollTopButtonVisible.value, isPeriodSelectorVisible.value) {
            appConfig {
                copy(
                    titleRes = R.string.page_cache_back_list_title,
                    floatingButtons = buildPersistentList {
                        add(
                            AppFloatingButton(
                                icon = RIcons.drawable.plus,
                                onClick = ::onAddCacheBack
                            )
                        )
                        if (isScrollTopButtonVisible.value) {
                            add(
                                AppFloatingButton(
                                    icon = RIcons.drawable.angle_up,
                                    onClick = { scope.launch { lazyListState.animateScrollToItem(0) } },
                                    type = DFPageFloatingButton.Type.GRAY,
                                )
                            )
                        }
                    },
                    additionalTitleContent = {
                        AnimatedVisibility(
                            visible = isPeriodSelectorVisible.value,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically(),
                        ) {
                            val text = remember { mutableStateOf("Май 2025") }
                            DFTextField(
                                text = text,
                                onChange = {}
                            )
                        }
                    }
                )
            }
        }

        val list = selectAsState<CacheBackState, LazyListItems>(
            initial = persistentListOf(),
            selector = { list.toUi(store) },
        )

        val error = selectAsState(CacheBackState::error)

        when (status.value) {
            LoadingStatus.Init -> CacheBackListInit()
            LoadingStatus.Loading -> CacheBackListLoading()
            LoadingStatus.Failed -> CacheBackListFailed(::onRefresh)
            LoadingStatus.Success -> CacheBackListSuccess(
                list = list,
                lazyListState = lazyListState
            )
        }
    }

    private fun onRefresh() = dispatch(loadBankListUseCase())
    private fun onAddCacheBack() = forward(AddCacheBackFeature())

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 5
