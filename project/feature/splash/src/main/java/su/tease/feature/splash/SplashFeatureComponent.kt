package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.state.MainFeatureNavigationTarget

class SplashFeatureComponent<S : State>(
    store: Store<S>,
) : BaseFeatureComponent<S>(
    store = store,
    target = MainFeatureNavigationTarget,
) {
    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        child()
    }
}
