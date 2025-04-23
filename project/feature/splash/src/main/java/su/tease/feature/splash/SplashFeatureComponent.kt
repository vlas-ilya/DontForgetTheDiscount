package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store

class SplashFeatureComponent(store: Store<*>) : BaseFeatureComponent(store) {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        child()
    }
}
