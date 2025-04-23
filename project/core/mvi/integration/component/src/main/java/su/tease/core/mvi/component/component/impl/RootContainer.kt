package su.tease.core.mvi.component.component.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.Component
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.store.Store

class RootContainer(
    private val store: Store<*>,
    private val root: BaseRootComponent,
    private val navigationTargetResolver: NavigationTargetResolver,
) : Component, RootContainerConfiguration {

    @Composable
    override fun Compose() {
        Box(modifier = Modifier.fillMaxSize()) {
            root.Compose {
                AppContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = this@RootContainer,
                ).Compose()
            }
        }
    }

    override var isFullscreen: Boolean
        get() = true
        set(value) {}
}
