package su.tease.project.feature.preset.presentation.icon.save

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetAction
import su.tease.project.feature.preset.presentation.icon.save.reducer.PageState
import su.tease.project.feature.preset.presentation.icon.save.reducer.SaveIconPresetState

class SaveIconPresetPage(
    store: Store<*>,
    target: Target,
    saveIconPresetAction: SaveIconPresetAction,
) : BasePageComponent<SaveIconPresetPage.Target>(store) {

    init {
        dispatch(saveIconPresetAction(target.iconOwner))
    }

    @Composable
    override fun invoke() {
        val state = selectAsState(SaveIconPresetState::pageState)

        LaunchedEffect(state.value) {
            when (val value = state.value) {
                is PageState.Init -> Unit
                is PageState.Finish -> back()
                is PageState.Saving -> {
                    when (value.status) {
                        LoadingStatus.Init -> Unit
                        LoadingStatus.Loading -> Unit
                        LoadingStatus.Success -> back()
                        LoadingStatus.Failed -> Unit
                    }
                }
            }
        }

        DFPage(
            title = stringResource(R.string.Presets_SaveIconPresetPage_title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {

        }
    }

    @Parcelize
    data class SelectIconTarget(val iconOwner: IconOwner) : NavigationTarget.Page

    @Parcelize
    data class Target(val iconOwner: IconOwner) : NavigationTarget.Page

    companion object {
        operator fun invoke(iconOwner: IconOwner) = Target(iconOwner)
    }
}