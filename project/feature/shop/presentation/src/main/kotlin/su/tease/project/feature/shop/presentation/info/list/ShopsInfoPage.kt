package su.tease.project.feature.shop.presentation.info.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.and
import su.tease.project.core.utils.utils.or
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.info.list.action.LoadShopsInfoAction
import su.tease.project.feature.shop.presentation.info.list.action.LoadShopsInfoActions
import su.tease.project.feature.shop.presentation.info.list.page.ShopsInfoPageFailed
import su.tease.project.feature.shop.presentation.info.list.page.ShopsInfoPageInit
import su.tease.project.feature.shop.presentation.info.list.page.ShopsInfoPageLoading
import su.tease.project.feature.shop.presentation.info.list.page.ShopsInfoPageSuccess
import su.tease.project.feature.shop.presentation.info.list.reducer.ShopsInfoPageState
import su.tease.project.feature.shop.presentation.info.list.utils.toUi
import su.tease.project.feature.shop.presentation.info.save.SaveShopInfoFeature
import su.tease.project.feature.shop.presentation.list.hasPrevPageSelector
import su.tease.project.design.icons.R as RIcons

class ShopsInfoPage(
    store: Store<*>,
    private val loadShopsInfoAction: LoadShopsInfoAction,
    private val resourceProvider: ResourceProvider,
    private val shopPresetIconView: ShopPresetIconView,
) : BasePageComponent<ShopsInfoPage.Target>(store) {

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    init {
        dispatch(LoadShopsInfoActions.OnLoad)
    }

    @Composable
    override fun invoke() {
        val status = selectAsState(ShopsInfoPageState::status)
        val list = selectAsState<ShopsInfoPageState, LazyListItems> {
            list.toUi(shopPresetIconView, store, ::onShopAccountClick)
        }
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

        val (_, _, resetScroll) = scrollDirectionState

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
                        onClick = { SaveShopInfoFeature().forward() },
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

        LaunchedEffect(this) { dispatch(loadShopsInfoAction()) }
        DFPage(
            title = stringResource(R.string.Shop_InfoListShopPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            showBackButton = selectAsState(hasPrevPageSelector).value,
        ) {
            val state = status.value
            when {
                state == LoadingStatus.Init -> ShopsInfoPageInit()
                state == LoadingStatus.Loading && list.value.isEmpty() -> ShopsInfoPageLoading()
                state == LoadingStatus.Failed -> ShopsInfoPageFailed({ onTryAgain() })
                else -> ShopsInfoPageSuccess(
                    list,
                    lazyListState,
                )
            }
        }
    }

    private fun onShopAccountClick(shop: Shop) =
        SaveShopInfoFeature(shop).forward()

    private fun onTryAgain() = dispatch(loadShopsInfoAction()).unit()

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
