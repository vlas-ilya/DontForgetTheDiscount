package su.tease.project.feature.preset.presentation.cashback.select

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
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.cashback.dialog.CashBackPresetInfoDialog
import su.tease.project.feature.preset.presentation.cashback.select.action.CreateCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.select.action.ExternalSelectCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.select.component.CashBackPresetPreview
import su.tease.project.feature.preset.presentation.cashback.select.reducer.CashBackPresetInfoDialogAction
import su.tease.project.feature.preset.presentation.cashback.select.reducer.SelectCashBackPresetState
import su.tease.project.feature.preset.presentation.cashback.select.utils.toUi
import su.tease.project.design.icons.R as RIcons

class SelectCashBackPresetPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
    private val createCashBackPresetAction: CreateCashBackPresetAction,
) : BasePageComponent<SelectCashBackPresetPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    override fun onFinish() {
        dispatch(ExternalSelectCashBackPresetAction.OnFinish)
    }

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val list = memoize { presetInteractor.cashBackPresets(target.ownerPreset.id) }
            .map { it?.toUi(store, ::onCashBackPresetClick) }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = {
                            dispatch(createCashBackPresetAction(target.ownerPreset))
                        }
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
            title = stringResource(R.string.Presets_SelectCashBackPreset_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {

            val cashBacks = list.value ?: run {
                lazyListWrapper.Shimmer(
                    count = SHIMMER_ITEM_COUNT,
                    modifier = Modifier.fillMaxWidth(),
                    itemContent = { CashBackPresetPreview.Shimmer(it) },
                    verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                    contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                )
                return@DFPage
            }
            lazyListWrapper.Compose(
                count = cashBacks.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = cashBacks::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            )
        }

        CashBackPresetInfoDialog(
            info = selectAsState(SelectCashBackPresetState::shownCashBack),
            onHide = { dispatch(CashBackPresetInfoDialogAction.OnHide) },
        )
    }

    private fun onCashBackPresetClick(cashBackPreset: CashBackPreset) {
        dispatch(ExternalSelectCashBackPresetAction.OnSelected(cashBackPreset))
        back()
    }

    @Parcelize
    data class Target(val ownerPreset: CashBackOwnerPreset) : NavigationTarget.Page

    companion object {
        operator fun invoke(ownerPreset: CashBackOwnerPreset) = Target(ownerPreset)
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
