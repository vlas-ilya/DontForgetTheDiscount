package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration

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
