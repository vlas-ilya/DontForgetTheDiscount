package su.tease.core.component.resolver.impl

import org.koin.core.scope.Scope
import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.component.component.provider.AppProvider
import su.tease.core.component.component.provider.FeatureProvider
import su.tease.core.component.component.provider.PageProvider
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.NavigationTarget

class AppNavigationTargetResolver(
    val scope: Scope,
    pages: List<PageProvider<*>>,
    features: List<FeatureProvider<*>>,
    apps: List<AppProvider<*>>,
) : NavigationTargetResolver {

    private val mapPage = pages.associateBy { it.target }
    private val mapFeatures = features.associateBy { it.target }
    private val mapApps = apps.associateBy { it.target }

    override fun <T: NavigationTarget.Page>resolve(page: T): BasePageComponent =
        mapPage[page::class.java]
            ?.let { it as? PageProvider<T> }
            ?.run { scope.component(page) }
            ?: error("There are no page component")

    override fun <T: NavigationTarget.Feature>resolve(feature: T): BaseFeatureComponent =
        mapFeatures[feature::class.java]
            ?.let { it as? FeatureProvider<T> }
            ?.run { scope.component(feature) }
            ?: error("There are no feature component")

    override fun <T: NavigationTarget.App>resolve(app: T): BaseAppComponent =
        mapApps[app::class.java]
            ?.let { it as? AppProvider<T> }
            ?.run { scope.component(app) }
            ?: error("There are no app component")
}

