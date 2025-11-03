package su.tease.project.feature.preset.presentation.icon.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.save.action.ExternalSaveIconPresetActions.OnFinish
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetAction

class SaveIconPresetPage(
    store: Store<*>,
    target: Target,
    saveIconPresetAction: SaveIconPresetAction,
) : BasePageComponent<SaveIconPresetPage.Target>(store) {

    init {
        dispatch(saveIconPresetAction(target.iconType))
    }

    override fun onFinish() {
        dispatch(OnFinish)
    }

    @Composable
    override fun invoke() {
        DFPage(
            title = stringResource(R.string.Presets_SaveIconPresetPage_title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            Shimmer {
                Box(
                    modifier = Modifier
                        .padding(Theme.sizes.padding8)
                        .clip(RoundedCornerShape(Theme.sizes.round12))
                        .fillMaxSize()
                        .background(Theme.colors.shimmer)
                )
            }
        }
    }

    @Parcelize
    data class Target(val iconType: IconType) : NavigationTarget.Page

    companion object {
        operator fun invoke(iconType: IconType) = Target(iconType)
    }
}