package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.Callback

abstract class BaseAppComponent(
    store: Store<*>,
) : BaseNavigationMviComponent(store) {

    open fun RootContainerConfiguration.configure() {
        isFullscreen = false
    }

    @Composable
    open fun ComposeNavigationBar() = Unit

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
    open operator fun invoke(child: @Composable Callback) = child()
}
