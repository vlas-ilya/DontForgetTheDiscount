package su.tease.dontforgetthediscount.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.impl.BaseRootComponent
import su.tease.project.core.mvi.api.store.Store

class DontForgetTheDiscountRootComponent(store: Store<*>) : BaseRootComponent(store) {
    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            child()
        }
    }
}
