package su.tease.project.feature.shop.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
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
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.defaultCashBackDate
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.action.SaveCacheBackAction
import su.tease.project.feature.shop.presentation.action.invoke
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.Compose
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.list.action.LoadShopsAction
import su.tease.project.feature.shop.presentation.list.action.LoadShopsActions
import su.tease.project.feature.shop.presentation.list.page.ListCashBackFailed
import su.tease.project.feature.shop.presentation.list.page.ListCashBackInit
import su.tease.project.feature.shop.presentation.list.page.ListCashBackLoading
import su.tease.project.feature.shop.presentation.list.page.ListCashBackSuccess
import su.tease.project.feature.shop.presentation.list.reducer.CashBackInfoDialogAction
import su.tease.project.feature.shop.presentation.list.reducer.ShopsState
import su.tease.project.feature.shop.presentation.list.utils.toCashBackDate
import su.tease.project.feature.shop.presentation.list.utils.toMonthYear
import su.tease.project.feature.shop.presentation.list.utils.toUi
import su.tease.project.design.icons.R as RIcons

class ShopsPage(
    store: Store<*>,
    private val loadShopList: LoadShopsAction,
    private val resourceProvider: ResourceProvider,
    private val dateProvider: DateProvider,
    private val cashBackPresetIconView: CashBackPresetIconView,
    private val shopPresetIconView: ShopPresetIconView,
    private val cashBackInfoDialogView: CashBackInfoDialogView,
    private val saveCacheBackAction: SaveCacheBackAction,
) : BasePageComponent<ShopsPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    init {
        dispatch(LoadShopsActions.OnDateSelect(dateProvider.current().toCashBackDate()))
    }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(ShopsState::status)
        val date = selectAsState(ShopsState::date)
        val dates = selectAsState(ShopsState::dates)
        val list = selectAsState(ShopsState::list)
            .map { it.toUi(date.value, shopPresetIconView, cashBackPresetIconView, store, saveCacheBackAction) }

        LaunchedEffect(date.value) {
            if (date.value === defaultCashBackDate) return@LaunchedEffect
            dispatch(loadShopList(date.value))
        }

        val isAddButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success || list.value.isNotEmpty()
            }
        }

        val (isScrollTopButtonVisible, scrollDirection, nestedScrollConnection, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = { dispatch(saveCacheBackAction(date = date.value)) },
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
            title = stringResource(R.string.Shop_ListShopPage_Title),
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
                        onItemClick = { dispatch(LoadShopsActions.OnDateSelect(it)) },
                        text = { dateProvider.toText(it.toMonthYear()) },
                        modifier = Modifier.padding(bottom = Theme.sizes.padding4),
                        background = Theme.colors.background2,
                        clip = RoundedCornerShape(Theme.sizes.round16)
                    )
                }
            }
        ) {
            val state = status.value
            when {
                state == LoadingStatus.Init -> ListCashBackInit(lazyListWrapper)
                state == LoadingStatus.Loading && list.value.isEmpty() -> ListCashBackLoading(
                    lazyListWrapper
                )

                state == LoadingStatus.Failed -> ListCashBackFailed({ onTryAgain(date) })
                else -> ListCashBackSuccess(
                    list,
                    lazyListWrapper,
                    Modifier.nestedScroll(nestedScrollConnection)
                )
            }
        }

        cashBackInfoDialogView.Compose(
            info = selectAsState(ShopsState::shownCashBack),
            onHide = { dispatch(CashBackInfoDialogAction.OnHide) },
            dateProvider = dateProvider,
        )
    }

    private fun onTryAgain(date: State<CashBackDate>) =
        dispatch(loadShopList(date.value)).unit()

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
