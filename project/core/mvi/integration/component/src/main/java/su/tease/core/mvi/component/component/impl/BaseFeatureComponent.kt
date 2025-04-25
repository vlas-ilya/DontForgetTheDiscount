package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.store.Store

abstract class BaseFeatureComponent(store: Store<*>) : BaseNavigationMviComponent(store) {

    open fun RootContainerConfiguration.configure() {}

    open fun AppContainerConfiguration.configure() {}

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
