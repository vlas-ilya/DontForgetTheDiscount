package su.tease.core.mvi.component.resolver.impl

import org.koin.core.scope.Scope
import su.tease.core.mvi.component.component.provider.AppProvider
import su.tease.core.mvi.component.component.provider.FeatureProvider
import su.tease.core.mvi.component.component.provider.PageProvider
import su.tease.core.mvi.component.component.provider.StoreTargetProvider
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

@Suppress("UNCHECKED_CAST")
class AppNavigationTargetResolver(
    val scope: Scope,
    pages: List<PageProvider<*>>,
    features: List<FeatureProvider<*>>,
    apps: List<AppProvider<*>>,
) : NavigationTargetResolver {

    private val store = scope.get<Store<*>>()

    private val mapPage = pages.associateBy { it.target }
    private val mapFeatures = features.associateBy { it.target }
    private val mapApps = apps.associateBy { it.target }

    override fun <T : NavigationTarget.Page> resolve(page: T) =
        mapPage[page::class.java]
            ?.let { it as? PageProvider<T> }
            ?.run { scope.component(StoreTargetProvider(page, store)) }
            ?: error("There are no page component")

    override fun <T : NavigationTarget.Feature> resolve(feature: T) =
        mapFeatures[feature::class.java]
            ?.let { it as? FeatureProvider<T> }
            ?.run { scope.component(StoreTargetProvider(feature, store)) }
            ?: error("There are no feature component")

    override fun <T : NavigationTarget.App> resolve(app: T) =
        mapApps[app::class.java]
            ?.let { it as? AppProvider<T> }
            ?.run { scope.component(StoreTargetProvider(app, store)) }
            ?: error("There are no app component")
}
