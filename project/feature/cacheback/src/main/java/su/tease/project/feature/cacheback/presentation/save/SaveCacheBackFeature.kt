package su.tease.project.feature.cacheback.presentation.save

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.presentation.save.cacheback.SaveCacheBackPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.design.icons.R as RIcons

class SaveCacheBackFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveCacheBackFeature.Target>(store) {

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

    private fun onClosePressed() = SaveCacheBackFeature().finish()

    companion object {
        operator fun invoke(saveCacheBackRequest: SaveCacheBackRequest = SaveCacheBackRequest()) =
            feature(
                Target,
                SaveCacheBackPage(saveCacheBackRequest),
            )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
