package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.project.core.mvi.api.store.Store

abstract class BaseRootComponent(store: Store<*>) : BaseMviComponent(store) {

    @Composable
    @Deprecated(
        message = "Use Compose(child)",
        replaceWith = ReplaceWith("Compose(child)"),
        level = DeprecationLevel.ERROR,
    )
    override operator fun invoke() {
        error("Not supported")
    }

    @Composable
    abstract operator fun invoke(child: @Composable () -> Unit)
}
