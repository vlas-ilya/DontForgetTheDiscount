package su.tease.core.component.component.impl

import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.component.utils.FeatureContainerConfiguration
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

abstract class BasePageComponent<S : State>(
    store: Store<S>,
    target: NavigationTarget.Page,
) : BaseNavigationMviComponent<S, NavigationTarget.Page>(store, target) {

    open fun RootContainerConfiguration.configure() {}
    open fun AppContainerConfiguration.configure() {}
    open fun FeatureContainerConfiguration.configure() {}
}
