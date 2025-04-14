package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.state.AppNavigationTarget

class SplashAppComponent<S : State>(
    store: Store<S>,
) : BaseAppComponent<S>(
    store = store,
    target = AppNavigationTarget,
) {
    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        child()
    }
}
