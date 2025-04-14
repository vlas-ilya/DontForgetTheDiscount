package su.tease.dontforgetthediscount.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.component.component.impl.RootContainer
import su.tease.core.component.component.impl.BaseMviComponent
import su.tease.design.theme.api.Theme
import su.tease.core.component.resolver.impl.AppNavigationTargetResolver
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountComponent<S : State>(
    store: Store<S>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
) : BaseMviComponent<S>(store) {

    @Composable
    override fun Compose() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(Theme.sizes.pagePadding)
        ) {
            RootContainer(
                store = this@DontForgetTheDiscountComponent,
                root = DontForgetTheDiscountRootComponent(
                    store = this@DontForgetTheDiscountComponent
                ),
                navigationTargetResolver = navigationTargetResolver,
            ).Compose()
        }
    }
}
