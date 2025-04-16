package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store

class SplashAppComponent<S : State>(
    store: Store<S>,
) : BaseAppComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        child()
    }
}
