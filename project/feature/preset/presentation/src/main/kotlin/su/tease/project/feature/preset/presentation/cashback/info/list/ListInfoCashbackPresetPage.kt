package su.tease.project.feature.preset.presentation.cashback.info.list

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.cashback.info.list.component.CashBackPresetPreview
import su.tease.project.feature.preset.presentation.cashback.info.list.reducer.ListInfoCashbackPresetState
import su.tease.project.feature.preset.presentation.cashback.info.save.SaveCashBackPresetFeature
import su.tease.project.feature.preset.presentation.cashback.info.list.component.dialog.CashBackPresetInfoDialog
import su.tease.project.feature.preset.presentation.cashback.info.list.reducer.CashBackPresetInfoDialogAction

class ListInfoCashbackPresetPage(
    store: Store<*>,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<ListInfoCashbackPresetPage.Target>(store) {

    @Composable
    override fun invoke() {
        val cashBacks by memoize { presetInteractor.cashBackPresets() }

        DFPage(
            title = stringResource(R.string.Presets_CashbackListPage_Title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = Theme.sizes.padding4)
            ) {
                if (cashBacks == null) {
                    item { SelectCashbackPresetShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }

                cashBacks?.forEach {
                    item(key = it.id) {
                        CashBackPresetPreview(
                            store = store,
                            cashBackPreset = it,
                            onClick = { SaveCashBackPresetFeature(it).forward() },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        CashBackPresetInfoDialog(
            info = selectAsState(ListInfoCashbackPresetState::shownCashBack),
            onHide = { dispatch(CashBackPresetInfoDialogAction.OnHide) },
        )
    }

    @Composable
    private fun SelectCashbackPresetShimmer(modifier: Modifier = Modifier) {
        Shimmer(
            modifier = modifier,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4)) {
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
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SHIMMER_ITEM_COUNT = 20
