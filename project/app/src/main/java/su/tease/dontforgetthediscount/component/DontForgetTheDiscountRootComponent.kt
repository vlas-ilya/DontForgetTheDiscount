package su.tease.dontforgetthediscount.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.impl.BaseRootComponent

class DontForgetTheDiscountRootComponent : BaseRootComponent() {
    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("RootComponent")
            child()
        }
    }
}
