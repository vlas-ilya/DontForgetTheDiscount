package su.tease.dontforgetthediscount.component

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import su.tease.core.mvi.component.component.container.RootContainer
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountComponent(
    private val store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
    private val windowProvider: () -> Window,
) {
    @Composable
    fun ComposeDontForgetTheDiscountComponent() {
        val rootContainer = remember {
            RootContainer(
                store = store,
                navigationTargetResolver = navigationTargetResolver,
                windowProvider = windowProvider,
            )
        }
        rootContainer.ComposeRootContainer()
    }
}
