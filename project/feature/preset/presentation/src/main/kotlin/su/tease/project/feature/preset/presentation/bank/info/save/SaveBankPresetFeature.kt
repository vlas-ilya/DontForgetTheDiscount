package su.tease.project.feature.preset.presentation.bank.info.save

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

class SaveBankPresetFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveBankPresetFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = R.drawable.cross_small,
                    onClick = ::onClosePressed,
                )
            )
        }
        child()
    }

    private fun onClosePressed() = SaveBankPresetFeature().finish()

    companion object {
        operator fun invoke(
            bankPreset: BankPreset? = null
        ) = feature(
            Target,
            SaveBankPresetPage(bankPreset),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
