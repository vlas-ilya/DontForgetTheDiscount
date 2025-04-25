package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.project.core.mvi.api.store.Store

class SplashAppComponent(store: Store<*>) : BaseAppComponent(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        child()
    }
}
