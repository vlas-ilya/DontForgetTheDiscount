package su.tease.project.feature.cacheback.presentation

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store

class CacheBackListPage<S : State>(
    store: Store<S>,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose() {

    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
