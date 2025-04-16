package su.tease.core.mvi.component.component.impl

import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.FeatureContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration

abstract class BasePageComponent : BaseNavigationMviComponent() {
    open fun RootContainerConfiguration.configure() {}

    open fun AppContainerConfiguration.configure() {}

    open fun FeatureContainerConfiguration.configure() {}
}
