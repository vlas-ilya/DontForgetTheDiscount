package su.tease.dontforgetthediscount.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import su.tease.core.mvi.component.component.container.RootContainer
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountComponent(
    private val store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
) {
    @Composable
    fun ComposeDontForgetTheDiscountComponent() {
        val rootContainer = remember { RootContainer(store, navigationTargetResolver) }
        rootContainer.ComposeRootContainer()
    }
}
