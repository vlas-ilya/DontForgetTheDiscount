package su.tease.project.feature.preset.impl.presentation.cashback.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.api.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.cashback.save.SaveCashBackPresetPage
import su.tease.project.feature.preset.impl.presentation.cashback.select.component.CashBackPresetPreview
import su.tease.project.feature.preset.impl.presentation.cashback.select.component.dialog.CashBackPresetInfoDialog
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.CashBackPresetInfoDialogAction
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.SelectCashBackPresetState
import su.tease.project.design.icons.R as RIcons

class SelectCashBackPresetPageImpl(
    store: Store<*>,
    private val target: SelectCashBackPresetPage.Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<SelectCashBackPresetPage.Target>(store), SelectCashBackPresetPage {

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val cashBacks by memoize { presetInterceptor.cashBackPresets(target.bankPreset.id) }
        val savedCashBackPreset = selectAsState(SelectCashBackPresetState::savedCashBackPreset).value

        LaunchedEffect(savedCashBackPreset) {
            if (savedCashBackPreset != null) {
                dispatch(SelectCashBackPresetPage.OnSelectAction(target.target, savedCashBackPreset))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveCashBackPresetPage(target.bankPreset).forward() }
                )
            )
        }

        DFPage(
            title = stringResource(R.string.page_cash_back_preset_list_title),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            if (cashBacks == null) {
                SelectCashBackPresetShimmer()
                return@DFPage
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            ) {
                cashBacks?.forEach {
                    item(key = it.id) {
                        CashBackPresetPreview(
                            store = store,
                            cashBackPreset = it,
                            onClick = {
                                dispatch(SelectCashBackPresetPage.OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        CashBackPresetInfoDialog(
            info = selectAsState(SelectCashBackPresetState::shownCashBack),
            onHide = { dispatch(CashBackPresetInfoDialogAction.OnHide) },
        )
    }

    @Composable
    private fun SelectCashBackPresetShimmer(modifier: Modifier = Modifier) {
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
                            .padding(horizontal = Theme.sizes.padding8)
                            .clip(RoundedCornerShape(Theme.sizes.round12))
                            .fillMaxWidth()
                            .height(Theme.sizes.size46)
                            .background(Theme.colors.shimmer)
                    )
                }
            }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
