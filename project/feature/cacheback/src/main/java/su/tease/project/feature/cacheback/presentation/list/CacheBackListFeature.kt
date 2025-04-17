package su.tease.project.feature.cacheback.presentation.list

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store

class CacheBackListFeature<S : State>(
    store: Store<S>,
) : BaseFeatureComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        child()
    }

    companion object {
        operator fun invoke() = feature(
            Target,
            CacheBackListPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
