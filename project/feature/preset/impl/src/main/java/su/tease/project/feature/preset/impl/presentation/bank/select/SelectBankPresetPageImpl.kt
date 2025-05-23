package su.tease.project.feature.preset.impl.presentation.bank.select

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
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.bank.component.SelectBankPresetPreview
import su.tease.project.feature.preset.impl.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.impl.presentation.bank.select.reducer.SelectBankPresetState
import su.tease.project.design.icons.R as RIcons

class SelectBankPresetPageImpl(
    store: Store<*>,
    private val target: SelectBankPresetPage.Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<SelectBankPresetPage.Target>(store), SelectBankPresetPage {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val banks by memoize { presetInterceptor.bankPresets() }
        val savedBankPreset = selectAsState(SelectBankPresetState::savedBankPreset).value

        LaunchedEffect(savedBankPreset) {
            if (savedBankPreset != null) {
                dispatch(SelectBankPresetPage.OnSelectAction(target.target, savedBankPreset))
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
                if (banks == null) {
                    item { SelectBankPresetShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }
                banks?.forEach {
                    item(key = it.id) {
                        SelectBankPresetPreview(
                            bankPreset = it,
                            onClick = {
                                dispatch(SelectBankPresetPage.OnSelectAction(target.target, it))
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
    }
}

private const val SHIMMER_ITEM_COUNT = 20
