package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.NavigationState
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.and
import su.tease.project.core.utils.utils.or
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toMonthYear
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListActions
import su.tease.project.feature.cacheback.presentation.list.component.dialog.CacheBackInfoDialog
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackFailed
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackInit
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackLoading
import su.tease.project.feature.cacheback.presentation.list.page.ListCacheBackSuccess
import su.tease.project.feature.cacheback.presentation.list.reducer.CacheBackInfoDialogAction
import su.tease.project.feature.cacheback.presentation.list.reducer.ListCacheBackState
import su.tease.project.feature.cacheback.presentation.list.utils.toUi
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.design.icons.R as RIcons

class ListCacheBackPage(
    store: Store<*>,
    private val loadBankAccountList: LoadBankAccountListAction,
    private val resourceProvider: ResourceProvider,
    private val dateProvider: DateProvider,
) : BasePageComponent<ListCacheBackPage.Target>(store) {

    init {
        dispatch(LoadBankAccountListActions.OnDateSelect(dateProvider.current().toCacheBackDate()))
    }

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(ListCacheBackState::status)
        val date = selectAsState(ListCacheBackState::date)
        val dates = selectAsState(ListCacheBackState::dates)
        val list = selectAsState<ListCacheBackState, LazyListItems> { list.toUi(date.value, store) }

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

        val isAddButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success || list.value.isNotEmpty()
            }
        }

        val (scrollDirection, nestedScrollConnection, resetScroll) = scrollDirectionState

        LaunchedEffect(date.value) {
            if (date.value === defaultCacheBackDate) return@LaunchedEffect
            dispatch(loadBankAccountList(date.value))
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
                        onClick = { SaveCacheBackFeature(SaveCacheBackRequest(date = date.value)).forward() },
                        isVisible = isAddButtonVisible.value
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
            showBackButton = selectAsState(hasPrevPageSelector).value,
            additionalTitleContent = {
                AnimatedVisibility(
                    visible = scrollDirection.value == ScrollDirection.BOTTOM,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    DFDropdownMenu(
                        selected = date.value,
                        items = dates.value,
                        onItemClick = { dispatch(LoadBankAccountListActions.OnDateSelect(it)) },
                        text = { dateProvider.toText(it.toMonthYear()) },
                        modifier = Modifier.padding(bottom = Theme.sizes.padding4),
                        background = Theme.colors.background1,
                        clip = RoundedCornerShape(Theme.sizes.round16)
                    )
                }
            }
        ) {
            val state = status.value
            when {
                state == LoadingStatus.Init -> ListCacheBackInit()
                state == LoadingStatus.Loading && list.value.isEmpty() -> ListCacheBackLoading()
                state == LoadingStatus.Failed -> ListCacheBackFailed({ onTryAgain(date) })
                else -> ListCacheBackSuccess(list, lazyListState, Modifier.nestedScroll(nestedScrollConnection))
            }
        }

        CacheBackInfoDialog(
            info = selectAsState(ListCacheBackState::shownCacheBack),
            onHide = { dispatch(CacheBackInfoDialogAction.OnHide) },
            dateProvider = dateProvider,
        )
    }

    private fun onTryAgain(date: State<CacheBackDate>) = dispatch(loadBankAccountList(date.value)).unit()

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3

val hasPrevPageSelector = Selector<NavigationState, Boolean> {
    val page = root.feature.stack.prev?.value?.page // Prev page in current feature
        ?: root.app.stack.prev?.value?.page // Last page in prev feature
        ?: root.stack.prev?.value?.page // Last page in prev app
    page != null
}
