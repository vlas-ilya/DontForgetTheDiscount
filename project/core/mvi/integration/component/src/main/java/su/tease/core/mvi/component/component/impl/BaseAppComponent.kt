package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

abstract class BaseAppComponent(
    store: Store<*>,
) : BaseNavigationMviComponent(store) {

    open fun RootContainerConfiguration.configure() {}

    @Composable
    open fun ComposeNavigationBar(target: NavigationTarget.Feature) {}

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
