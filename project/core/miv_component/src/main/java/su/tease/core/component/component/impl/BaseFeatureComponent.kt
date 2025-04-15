package su.tease.core.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.component.utils.RootContainerConfiguration

abstract class BaseFeatureComponent : BaseNavigationMviComponent() {

    open fun RootContainerConfiguration.configure() {}
    open fun AppContainerConfiguration.configure() {}

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
