package su.tease.project.feature.shop.presentation.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.list.LazyList
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.component.ShopsItem
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.info.list.utils.toUi
import su.tease.project.feature.shop.presentation.save.SaveShopPage
import su.tease.project.feature.shop.presentation.select.reducer.SelectShopPageReducer
import su.tease.project.feature.shop.presentation.select.reducer.SelectShopState
import su.tease.project.design.icons.R as RIcons

class SelectShopPage(
    store: Store<*>,
    private val target: Target,
    private val interceptor: ShopInterceptor,
    private val resourceProvider: ResourceProvider,
    private val shopPresetIconView: ShopPresetIconView,
) : BasePageComponent<SelectShopPage.Target>(store) {

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    init {
        dispatch(OnInit)
    }

    @Composable
    override fun invoke() {
        val savedShop = selectAsState(SelectShopState::savedShop).value
        val list = memoize { interceptor.list() }
            .map { it?.toUi(shopPresetIconView, store, ::onShopClick) }

        LaunchedEffect(savedShop) {
            if (savedShop != null) {
                dispatch(OnSelectAction(target.target, savedShop))
                back()
            }
        }

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
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
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveShopPage<SelectShopPageReducer>().forward() }
                ),
                DFPageFloatingButton(
                    icon = RIcons.drawable.angle_up,
                    onClick = { scrollUp() },
                    type = DFPageFloatingButton.Type.GRAY,
                    isVisible = isScrollTopButtonVisible.value
                )
            )
        }

        DFPage(
            title = stringResource(R.string.Shop_SelectShopPage_Title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            floatingButtons = floatingButtons,
        ) {
            val shopw = list.value ?: run {
                SelectShopShimmer()
                return@DFPage
            }

            LazyList(
                count = shopw.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = shopw::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                lazyListState = lazyListState,
            )
        }
    }

    private fun onShopClick(shop: Shop) {
        dispatch(OnSelectAction(target.target, shop))
        back()
    }

    @Composable
    private fun SelectShopShimmer(modifier: Modifier = Modifier) {
        Shimmer(
            modifier = modifier,
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding4))
                repeat(SHIMMER_ITEM_COUNT) { ShopsItem.Shimmer() }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: Shop?,
    ) : PlainAction

    @Parcelize
    data object OnInit : PlainAction

    companion object {
        inline operator fun <reified T> invoke() = Target(T::class.java.name)
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
