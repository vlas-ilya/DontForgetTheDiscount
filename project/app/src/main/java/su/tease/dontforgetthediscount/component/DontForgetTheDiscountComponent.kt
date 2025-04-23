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
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.impl.BaseMviComponent
import su.tease.core.mvi.component.component.impl.RootContainer
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountComponent(
    store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
) : BaseMviComponent(store) {

    @Composable
    override fun Compose() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(Theme.colors.background),
        ) {
            RootContainer(
                store = store,
                root = DontForgetTheDiscountRootComponent(store),
                navigationTargetResolver = navigationTargetResolver,
            ).Compose()
        }
    }
}
