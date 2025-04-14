package su.tease.core.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

abstract class BaseFeatureComponent<S : State>(
    store: Store<S>,
    target: NavigationTarget.Feature,
) : BaseNavigationMviComponent<S, NavigationTarget.Feature>(store, target) {

    open fun RootContainerConfiguration.configure() { }
    open fun AppContainerConfiguration.configure() { }

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
