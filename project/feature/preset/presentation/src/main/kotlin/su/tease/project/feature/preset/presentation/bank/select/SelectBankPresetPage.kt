package su.tease.project.feature.preset.presentation.bank.select

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
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.component.SelectBankPresetPreview
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.reducer.SelectBankPresetState
import su.tease.project.design.icons.R as RIcons

class SelectBankPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<SelectBankPresetPage.Target>(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val banks by memoize { presetInteractor.bankPresets() }
        val savedBankPreset = selectAsState(SelectBankPresetState::savedCashBackOwnerPreset).value

        LaunchedEffect(savedBankPreset) {
            if (savedBankPreset != null) {
                dispatch(OnSelectAction(target.target, savedBankPreset))
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

    @Parcelize
    data class Target(
        val target: String,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: BankPreset? = null,
        ) = Target(T::class.java.name, selected)
    }
}

private const val SHIMMER_ITEM_COUNT = 20
