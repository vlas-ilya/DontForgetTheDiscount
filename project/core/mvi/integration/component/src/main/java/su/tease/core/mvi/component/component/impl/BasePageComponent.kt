package su.tease.core.mvi.component.component.impl

import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.FeatureContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.store.Store

abstract class BasePageComponent(
    store: Store<*>
) : BaseNavigationMviComponent(store) {

    open fun RootContainerConfiguration.configure() {
        isFullscreen = false
    }

    open fun AppContainerConfiguration.configure() {
        hasNavigationBar = true
    }

    open fun FeatureContainerConfiguration.configure() = Unit
}
