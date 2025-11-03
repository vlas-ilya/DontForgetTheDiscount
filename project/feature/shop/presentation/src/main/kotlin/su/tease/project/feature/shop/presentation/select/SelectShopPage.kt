package su.tease.project.feature.shop.presentation.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.component.ShopsItem
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.info.list.utils.toUi
import su.tease.project.feature.shop.presentation.select.action.CreateShopAction
import su.tease.project.feature.shop.presentation.select.action.ExternalSelectShopActions
import su.tease.project.design.icons.R as RIcons

class SelectShopPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val interceptor: ShopInterceptor,
    private val shopPresetIconView: ShopPresetIconView,
    private val createShopAction: CreateShopAction,
) : BasePageComponent<SelectShopPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    override fun onFinish() {
        dispatch(ExternalSelectShopActions.OnFinish)
    }

    @Composable
    override fun invoke() {
        val list = memoize { interceptor.list() }
            .map { it?.toUi(shopPresetIconView, store, ::onShopClick) }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { dispatch(createShopAction()) }
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
            val shops = list.value ?: run {
                lazyListWrapper.Shimmer(
                    count = SHIMMER_ITEM_COUNT,
                    modifier = Modifier.fillMaxWidth(),
                    itemContent = { ShopsItem.Shimmer(it) },
                    verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                    contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                )
                return@DFPage
            }


            lazyListWrapper.Compose(
                count = shops.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = shops::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            )
        }
    }

    private fun onShopClick(shop: Shop) {
        dispatch(ExternalSelectShopActions.OnSelected(shop))
        back()
    }

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
