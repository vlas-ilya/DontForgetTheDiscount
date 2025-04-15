package su.tease.core.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

abstract class BaseRootComponent : BaseMviComponent() {

    @Composable
    @Deprecated(
        message = "Use Compose(child)",
        replaceWith = ReplaceWith("Compose(child)"),
        level = DeprecationLevel.ERROR,
    )
    override fun Compose() {
        error("Not supported")
    }

    @Composable
    abstract fun Compose(child: @Composable () -> Unit)
}