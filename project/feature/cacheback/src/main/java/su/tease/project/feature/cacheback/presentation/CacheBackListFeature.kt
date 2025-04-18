package su.tease.project.feature.cacheback.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.usecase.ListCacheBackUseCase

class CacheBackListFeature<S : State>(
    store: Store<S>,
    private val listCacheBackUseCase: ListCacheBackUseCase,
) : BaseFeatureComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        LaunchedEffect(Unit) {
            listCacheBackUseCase()
        }
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
