@file:Suppress("UnusedPrivateProperty")

package su.tease.project.feature.preset.presentation.shop.select

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor

class SelectShopPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<SelectShopPresetPage.Target>(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        /*val shops by memoize { presetInteractor.shopPresets() }
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
                    onClick = { SaveBankPresetPage().forward() }
                )
            )
        }

        DFPage(
            title = stringResource(R.string.page_select_bank_preset_title),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            LazyColumn {
                if (shops == null) {
                    item { SelectBankPresetShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }
                shops?.forEach {
                    item(key = it.id) {
                        SelectBankPresetPreview(
                            cashBackOwnerPreset = it,
                            onClick = {
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }*/
    }
    /*
        @Composable
        private fun SelectBankPresetShimmer(modifier: Modifier = Modifier) {
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
        }*/

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
            selected: ShopPreset?,
        ) = Target(T::class.java.name, selected)
    }
}

private const val SHIMMER_ITEM_COUNT = 20
