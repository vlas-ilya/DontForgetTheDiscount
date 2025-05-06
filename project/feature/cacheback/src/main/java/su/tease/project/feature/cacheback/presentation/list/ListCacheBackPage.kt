package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.buildPersistentList
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toMonthYear
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.AddCacheBackFeature
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackFailed
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackInit
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackSuccess
import su.tease.project.feature.cacheback.presentation.mapper.toUi
import su.tease.project.design.icons.R as RIcons

class ListCacheBackPage(
    store: Store<*>,
    private val loadBankListUseCase: LoadBankListUseCase,
    private val resourceProvider: ResourceProvider,
    private val dateProvider: DateProvider,
) : BasePageComponent(store) {

    init {
        dispatch(LoadBankListAction.OnDateSelect(dateProvider.current().toCacheBackDate()))
    }

    private val lazyListState = LazyListState(
        firstVisibleItemIndex = 0,
        firstVisibleItemScrollOffset = 0,
    )

    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    @Composable
    override operator fun invoke() {
        val scope = rememberCoroutineScope()

        val status = selectAsState(CacheBackState::status)
        val date = selectAsState(CacheBackState::date)
        val dates = selectAsState(CacheBackState::dates)

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success &&
                    lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
            }
        }

        val (scrollDirection, nestedScrollConnection, resetScroll) = scrollDirectionState

        LaunchedEffect(date.value) {
            if (date.value !== defaultCacheBackDate) {
                dispatch(loadBankListUseCase(date.value))
            }
        }

        LaunchedEffect(isScrollTopButtonVisible.value, scrollDirection.value) {
            appConfig {
                copy(
                    titleRes = R.string.page_cache_back_list_title,
                    floatingButtons = buildPersistentList {
                        add(
                            DFPageFloatingButton(
                                icon = RIcons.drawable.plus,
                                onClick = {
                                    forward(
                                        AddCacheBackFeature(
                                            addFormState = AddFormState(
                                                date = date.value
                                            )
                                        )
                                    )
                                }
                            )
                        )
                        add(
                            DFPageFloatingButton(
                                icon = RIcons.drawable.angle_up,
                                onClick = {
                                    scope.launch {
                                        resetScroll()
                                        lazyListState.animateScrollToItem(0)
                                    }
                                },
                                type = DFPageFloatingButton.Type.GRAY,
                                isVisible = isScrollTopButtonVisible.value
                            )
                        )
                    },
                    additionalTitleContent = {
                        AnimatedVisibility(
                            visible = scrollDirection.value == ScrollDirection.BOTTOM,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically(),
                        ) {
                            DFDropdownMenu(
                                selected = date.value,
                                items = dates.value,
                                onItemClick = { dispatch(LoadBankListAction.OnDateSelect(it)) },
                                text = { dateProvider.toText(it.toMonthYear()) },
                                modifier = Modifier.padding(bottom = Theme.sizes.padding4),
                                background = Theme.colors.background1,
                            )
                        }
                    }
                )
            }
        }

        val list = selectAsState<CacheBackState, LazyListItems> { list.toUi(date.value, store) }

        val error = selectAsState(CacheBackState::error)

        when (status.value) {
            LoadingStatus.Init -> ListCacheBackInit()

            LoadingStatus.Failed -> ListCacheBackFailed(error) {
                dispatch(loadBankListUseCase(date.value))
            }

            LoadingStatus.Loading,
            LoadingStatus.Success -> ListCacheBackSuccess(
                isLoading = status.value == LoadingStatus.Loading,
                list = list,
                lazyListState = lazyListState,
                modifier = Modifier.nestedScroll(nestedScrollConnection)
            )
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
