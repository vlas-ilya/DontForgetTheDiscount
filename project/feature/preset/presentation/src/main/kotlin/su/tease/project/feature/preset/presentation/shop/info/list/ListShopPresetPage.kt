package su.tease.project.feature.preset.presentation.shop.info.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.shop.component.ShopPresetPreview
import su.tease.project.feature.preset.presentation.shop.info.save.SaveShopPresetFeature
import su.tease.project.feature.preset.presentation.shop.utils.toUi
import su.tease.project.design.icons.R as RIcons

class ListShopPresetPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<ListShopPresetPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    @Composable
    override fun invoke() {
        val list = memoize { presetInteractor.shopPresets() }
            .map { list -> list?.toUi(store, { SaveShopPresetFeature(it).forward() }) }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = { SaveShopPresetFeature().forward() }
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
            title = stringResource(R.string.Presets_ShopListPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            val shops = list.value ?: run {
                lazyListWrapper.Shimmer(
                    count = SHIMMER_ITEM_COUNT,
                    modifier = Modifier.fillMaxWidth(),
                    itemContent = { ShopPresetPreview.Shimmer(it) },
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

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
