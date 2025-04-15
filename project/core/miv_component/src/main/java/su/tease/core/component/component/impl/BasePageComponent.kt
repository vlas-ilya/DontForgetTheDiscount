package su.tease.core.component.component.impl

import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.component.utils.FeatureContainerConfiguration
import su.tease.core.component.utils.RootContainerConfiguration

abstract class BasePageComponent : BaseNavigationMviComponent() {

    open fun RootContainerConfiguration.configure() {}
    open fun AppContainerConfiguration.configure() {}
    open fun FeatureContainerConfiguration.configure() {}
}
