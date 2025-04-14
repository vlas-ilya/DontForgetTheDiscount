package su.tease.core.component.resolver.impl

import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.NavigationTarget

class AppNavigationTargetResolver(
    private val resolvers: List<NavigationTargetResolver>
) : NavigationTargetResolver {

    override fun resolve(page: NavigationTarget.Page): BasePageComponent<*>? =
        resolvers
            .asSequence()
            .mapNotNull { it.resolve(page) }
            .firstOrNull()

    override fun resolve(feature: NavigationTarget.Feature): BaseFeatureComponent<*>? =
        resolvers
            .asSequence()
            .mapNotNull { it.resolve(feature) }
            .firstOrNull()

    override fun resolve(app: NavigationTarget.App): BaseAppComponent<*>? =
        resolvers
            .asSequence()
            .mapNotNull { it.resolve(app) }
            .firstOrNull()
}
