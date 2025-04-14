package su.tease.core.component.resolver

import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget

interface NavigationTargetResolver {
    fun resolve(page: NavigationTarget.Page): BasePageComponent<*>? = null
    fun resolve(feature: NavigationTarget.Feature): BaseFeatureComponent<*>? = null
    fun resolve(app: NavigationTarget.App): BaseAppComponent<*>? = null
}
