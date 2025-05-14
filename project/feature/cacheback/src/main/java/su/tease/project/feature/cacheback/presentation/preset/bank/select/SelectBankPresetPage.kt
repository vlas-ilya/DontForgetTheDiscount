package su.tease.project.feature.cacheback.presentation.preset.bank.select

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.interceptor.PresetInterceptor
import su.tease.project.feature.cacheback.presentation.preset.bank.component.SelectBankPresetPreview
import su.tease.project.feature.cacheback.presentation.preset.bank.save.SaveBankPresetPage
import su.tease.project.feature.cacheback.presentation.preset.bank.select.reducer.SelectBankPresetState
import su.tease.project.design.icons.R as RIcons

class SelectBankPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<SelectBankPresetPage.Target>(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val banks by memoize { presetInterceptor.bankPresets() }
        val savedBankPreset = selectAsState(SelectBankPresetState::savedBankPreset).value

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
            title = stringResource(target.pageTitle),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            LazyColumn(contentPadding = PaddingValues(horizontal = Theme.sizes.padding4)) {
                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }
                banks?.forEach {
                    item(key = it.id) {
                        SelectBankPresetPreview(
                            bankPreset = it,
                            onClick = {
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        @StringRes val pageTitle: Int,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            @StringRes pageTitle: Int,
            selected: BankPreset?,
        ) = Target(T::class.java.name, pageTitle, selected)
    }
}
