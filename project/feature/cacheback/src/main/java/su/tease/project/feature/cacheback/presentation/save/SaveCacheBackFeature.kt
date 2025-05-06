package su.tease.project.feature.cacheback.presentation.save

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.AppAction
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackState
import su.tease.project.design.icons.R as RIcons

class SaveCacheBackFeature(
    store: Store<*>,
) : BaseFeatureComponent(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        LaunchedEffect(Unit) {
            rootConfig {
                copy(
                    isFullscreen = true
                )
            }
            appConfig {
                copy(
                    hasNavigationBar = false,
                    action = AppAction(
                        icon = RIcons.drawable.cross_small,
                        onClick = ::onClosePressed,
                    )
                )
            }
        }

        child()
    }

    private fun onClosePressed() = finish(SaveCacheBackFeature())

    companion object {
        operator fun invoke(addFormState: SaveCacheBackState = SaveCacheBackState()) = feature(
            Target,
            SaveCacheBackPage.Target(addFormState),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
