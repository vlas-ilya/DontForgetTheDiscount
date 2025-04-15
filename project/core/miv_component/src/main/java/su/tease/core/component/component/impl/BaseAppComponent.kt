package su.tease.core.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget

abstract class BaseAppComponent : BaseNavigationMviComponent() {

    open fun RootContainerConfiguration.configure() {}

    @Composable
    open fun ComposeNavigationBar(target: NavigationTarget.Feature) {
    }

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
