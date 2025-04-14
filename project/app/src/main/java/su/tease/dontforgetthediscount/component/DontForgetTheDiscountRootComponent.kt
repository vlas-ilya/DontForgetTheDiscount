package su.tease.dontforgetthediscount.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.component.component.impl.BaseRootComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountRootComponent<S: State>(
    store: Store<S>,
): BaseRootComponent<S>(store) {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("RootComponent")
            child()
        }
    }
}
