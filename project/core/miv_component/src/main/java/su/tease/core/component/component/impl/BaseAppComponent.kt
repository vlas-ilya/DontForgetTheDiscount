package su.tease.core.component.component.impl

import androidx.compose.runtime.Composable
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

abstract class BaseAppComponent<S : State>(
    store: Store<S>,
    target: NavigationTarget.App,
) : BaseNavigationMviComponent<S, NavigationTarget.App>(store, target) {

    open fun RootContainerConfiguration.configure() {}

    @Composable
    open fun NavigationBar(target: NavigationTarget.Feature) {}

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
