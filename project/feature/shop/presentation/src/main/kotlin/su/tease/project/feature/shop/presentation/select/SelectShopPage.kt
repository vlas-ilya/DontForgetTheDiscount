package su.tease.project.feature.shop.presentation.select

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
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.save.SaveShopPage
import su.tease.project.feature.shop.presentation.select.component.SelectShopPageShopPresetPreview
import su.tease.project.feature.shop.presentation.select.reducer.SelectShopState
import su.tease.project.design.icons.R as RIcons

class SelectShopPage(
    store: Store<*>,
    private val target: Target,
    private val interceptor: ShopInterceptor,
    private val shopPresetIconView: ShopPresetIconView,
) : BasePageComponent<SelectShopPage.Target>(store) {

    @Composable
    override fun invoke() {
        val shops by memoize { interceptor.list() }
        val savedShop = selectAsState(SelectShopState::savedShop).value

        LaunchedEffect(savedShop) {
            if (savedShop != null) {
                dispatch(OnSelectAction(target.target, savedShop))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveShopPage().forward() }
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
            LazyColumn {
                if (shops == null) {
                    item { SelectShopShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }

                shops?.forEach {
                    item(key = it.id) {
                        val shopPreset = remember(it) { it.preset.copy(name = it.customName) }
                        SelectShopPageShopPresetPreview(
                            shopPreset = shopPreset,
                            onClick = {
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            },
                            shopPresetIconView = shopPresetIconView,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
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
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: Shop?,
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke() = Target(T::class.java.name)
    }
}

private const val SHIMMER_ITEM_COUNT = 20
