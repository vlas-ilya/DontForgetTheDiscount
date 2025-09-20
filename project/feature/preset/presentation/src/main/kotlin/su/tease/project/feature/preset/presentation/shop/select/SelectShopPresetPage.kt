package su.tease.project.feature.preset.presentation.shop.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.shop.component.SelectShopPresetPreview
import su.tease.project.feature.preset.presentation.shop.save.SaveShopPresetPage
import su.tease.project.feature.preset.presentation.shop.select.reducer.SelectShopPresetState
import su.tease.project.design.icons.R as RIcons

class SelectShopPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<SelectShopPresetPage.Target>(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val shops by memoize { presetInteractor.shopPresets() }
        val savedShopPreset = selectAsState(SelectShopPresetState::savedCashBackOwnerPreset).value

        LaunchedEffect(savedShopPreset) {
            if (savedShopPreset != null) {
                dispatch(OnSelectAction(target.target, savedShopPreset))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveShopPresetPage().forward() }
                )
            )
        }

        DFPage(
            title = stringResource(R.string.page_select_shop_preset_title),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            LazyColumn {
                if (shops == null) {
                    item { SelectShopPresetShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }
                shops?.forEach {
                    item(key = it.id) {
                        SelectShopPresetPreview(
                            shopPreset = it,
                            onClick = {
                                dispatch(SelectShopPresetPage.OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun SelectShopPresetShimmer(modifier: Modifier = Modifier) {
        Shimmer(
            modifier = modifier,
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding8))
                repeat(SHIMMER_ITEM_COUNT) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Theme.sizes.padding2)
                            .clip(RoundedCornerShape(Theme.sizes.round12))
                            .fillMaxWidth()
                            .height(Theme.sizes.size42)
                            .background(Theme.colors.shimmer)
                    )
                }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: ShopPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: ShopPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: ShopPreset? = null,
        ) = Target(T::class.java.name, selected)
    }
}

private const val SHIMMER_ITEM_COUNT = 20
