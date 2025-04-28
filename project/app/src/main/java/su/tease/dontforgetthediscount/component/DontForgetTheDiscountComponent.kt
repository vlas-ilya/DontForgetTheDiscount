package su.tease.dontforgetthediscount.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.container.AppContainer
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountComponent(
    private val store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
) : RootContainerConfiguration {

    @Composable
    fun ComposeDontForgetTheDiscountComponent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(Theme.colors.background),
        ) {
            val appContainer = remember {
                AppContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = this@DontForgetTheDiscountComponent,
                )
            }
            appContainer.ComposeAppContainer()
        }
    }

    override var isFullscreen: Boolean
        get() = true
        set(value) {}
}
