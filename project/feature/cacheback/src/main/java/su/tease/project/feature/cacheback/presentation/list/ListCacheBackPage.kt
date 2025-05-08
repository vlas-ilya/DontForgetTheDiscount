package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.and
import su.tease.project.core.utils.utils.or
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toMonthYear
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.presentation.list.component.CacheBackDialogContent
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackFailed
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackInit
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackSuccess
import su.tease.project.feature.cacheback.presentation.mapper.toUi
import su.tease.project.feature.cacheback.presentation.reducer.CacheBackInfoDialogAction
import su.tease.project.feature.cacheback.presentation.reducer.ListCacheBackState
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackState
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
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

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(ListCacheBackState::status)
        val date = selectAsState(ListCacheBackState::date)
        val dates = selectAsState(ListCacheBackState::dates)
        val list = selectAsState<ListCacheBackState, LazyListItems> { list.toUi(date.value, store) }
        val error = selectAsState(ListCacheBackState::error)

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                and(
                    or(
                        status.value == LoadingStatus.Success,
                        status.value == LoadingStatus.Loading,
                    ),
                    lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
                )
            }
        }

        val (scrollDirection, nestedScrollConnection, resetScroll) = scrollDirectionState

        LaunchedEffect(date.value) {
            if (date.value === defaultCacheBackDate) return@LaunchedEffect
            dispatch(loadBankListUseCase(date.value))
        }

        val scope = rememberCoroutineScope()
        val scrollUp = rememberCallback(resetScroll, lazyListState) {
            scope.launch {
                resetScroll()
                lazyListState.animateScrollToItem(0)
            }
        }

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = {
                            forward(
                                SaveCacheBackFeature(
                                    addFormState = SaveCacheBackState(
                                        date = date.value
                                    )
                                )
                            )
                        }
                    ),
                    DFPageFloatingButton(
                        icon = RIcons.drawable.angle_up,
                        onClick = { scrollUp() },
                        type = DFPageFloatingButton.Type.GRAY,
                        isVisible = isScrollTopButtonVisible.value
                    )
                )
            }
        }

        DFPage(
            title = stringResource(R.string.page_cache_back_list_title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
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
                        clip = RoundedCornerShape(Theme.sizes.round16)
                    )
                }
            }
        ) {
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

        DFBottomSheet(
            data = selectAsState(ListCacheBackState::shownCacheBack).value,
            onHide = { dispatch(CacheBackInfoDialogAction.OnHide) }
        ) { CacheBackDialogContent(data, ::dismiss, dateProvider) }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
