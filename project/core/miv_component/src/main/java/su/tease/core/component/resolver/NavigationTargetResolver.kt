package su.tease.core.component.resolver

import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget

interface NavigationTargetResolver {
    fun <T: NavigationTarget.Page>resolve(page: T): BasePageComponent
    fun <T: NavigationTarget.Feature>resolve(feature: T): BaseFeatureComponent
    fun <T: NavigationTarget.App>resolve(app: T): BaseAppComponent
}
