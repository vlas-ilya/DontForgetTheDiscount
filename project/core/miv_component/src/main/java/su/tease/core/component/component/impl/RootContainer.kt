package su.tease.core.component.component.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import su.tease.core.component.component.Component
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.selector.select
import su.tease.project.core.mvi_navigation.selector.page
import su.tease.project.core.mvi_navigation.state.FinishNavigationTarget

class RootContainer<S : State>(
    private val store: Store<S>,
    private val root: BaseRootComponent<S>,
    private val navigationTargetResolver: NavigationTargetResolver,
) : Component, RootContainerConfiguration {

    @Composable
    override fun Compose() {
        val pageTarget = store.select(page()).collectAsState(null).value

        if (pageTarget?.name == FinishNavigationTarget) return

        Box(modifier = Modifier.fillMaxSize()) {
            root.Compose {
                AppContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = this@RootContainer
                ).Compose()
            }
        }
    }

    override var isFullscreen: Boolean
        get() = true
        set(value) {}
}
