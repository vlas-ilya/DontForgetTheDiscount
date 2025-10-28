package su.tease.project.feature.preset.presentation.icon.info.save

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.icons.R
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage.Companion.invoke
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner

class SaveIconPresetFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveIconPresetFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = R.drawable.cross_small,
                    onClick = { finish() },
                )
            )
        }
        child()
    }

    companion object {
        operator fun invoke(
            iconOwner: IconOwner
        ) = feature(
            Target,
            SaveIconPresetPage(iconOwner),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
