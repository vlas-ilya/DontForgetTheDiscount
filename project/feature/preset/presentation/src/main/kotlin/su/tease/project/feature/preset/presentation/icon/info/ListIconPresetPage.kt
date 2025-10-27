package su.tease.project.feature.preset.presentation.icon.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.grid.LazyVerticalGridWrapper
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.preset.domain.entity.IconPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.design.icons.R as RIcons

class ListIconPresetPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<ListIconPresetPage.Target>(store) {

    private val lazyVerticalGridWrapper =
        LazyVerticalGridWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    @Composable
    override operator fun invoke() {
        val list by memoize { target.iconType.getIcons(presetInteractor) }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyVerticalGridWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
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
            title = stringResource(
                when (target.iconType) {
                    IconType.BANK_ICON -> R.string.Presets_ListIconPresetPage_Bank_Title
                    IconType.SHOP_ICON -> R.string.Presets_ListIconPresetPage_Shop_Title
                    IconType.CASH_BACK_ICON -> R.string.Presets_ListIconPresetPage_Cashback_Title
                }
            ),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            val icons = list ?: run {
                lazyVerticalGridWrapper.Shimmer(
                    columns = GridCells.Adaptive(minSize = Theme.sizes.size40),
                    contentPadding = PaddingValues(Theme.sizes.padding6),
                    verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                    horizontalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                ) {
                    repeat(SHIMMER_ITEM_COUNT) {
                        item(it) {
                            Box(
                                modifier = Modifier
                                    .clip(target.iconType.clip())
                                    .background(Theme.colors.shimmer)
                                    .padding(target.iconType.padding())
                                    .size(target.iconType.size()),
                            )
                        }
                    }
                }
                return@DFPage
            }
            lazyVerticalGridWrapper.Compose(
                columns = GridCells.Adaptive(minSize = Theme.sizes.size40),
                contentPadding = PaddingValues(Theme.sizes.padding6),
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                horizontalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
            ) {
                icons.forEach {
                    item(key = it.iconUrl) {
                        DFImage(
                            modifier = Modifier
                                .clip(target.iconType.clip())
                                .padding(target.iconType.padding())
                                .size(target.iconType.size()),
                            url = it.iconUrl,
                            contentDescription = "",
                            tint = target.iconType.tint(),
                        )
                    }
                }
            }
        }
    }

    enum class IconType(
        val getIcons: suspend (PresetInteractor) -> PersistentList<IconPreset>,
        val tint: @Composable () -> Color?,
        val clip: @Composable () -> Shape,
        val padding: @Composable () -> Dp,
        val size: @Composable () -> Dp,
    ) {
        BANK_ICON(
            getIcons = PresetInteractor::bankIconPresets,
            tint = { null },
            clip = { CircleShape },
            padding = { 0.dp },
            size = { Theme.sizes.size46 },
        ),
        SHOP_ICON(
            getIcons = PresetInteractor::shopIconPresets,
            tint = { null },
            clip = { CircleShape },
            padding = { 0.dp },
            size = { Theme.sizes.size46 },
        ),
        CASH_BACK_ICON(
            getIcons = PresetInteractor::cashBacksIconPresets,
            tint = { Theme.colors.iconTint },
            clip = { RoundedCornerShape(Theme.sizes.round4) },
            padding = { Theme.sizes.padding4 },
            size = { Theme.sizes.size46 },
        ),
    }

    @Parcelize
    data class Target(val iconType: IconType) : NavigationTarget.Page

    companion object {
        operator fun invoke(iconType: IconType) = Target(iconType)
    }
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 16
private const val SHIMMER_ITEM_COUNT = 200