package su.tease.project.feature.cashback.presentation.save

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cashback.presentation.save.cashback.SaveCashBackPage
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest
import su.tease.project.design.icons.R as RIcons

class SaveCashBackFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveCashBackFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = RIcons.drawable.cross_small,
                    onClick = ::onClosePressed,
                )
            )
        }
        child()
    }

    private fun onClosePressed() = SaveCashBackFeature().finish()

    companion object {
        operator fun invoke(saveCashBackRequest: SaveCashBackRequest = SaveCashBackRequest()) =
            feature(
                Target,
                SaveCashBackPage(saveCashBackRequest),
            )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
