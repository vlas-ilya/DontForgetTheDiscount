package su.tease.core.mvi.component.resolver.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.component.provider.AppProvider
import su.tease.core.mvi.component.component.provider.FeatureProvider
import su.tease.core.mvi.component.component.provider.NavigationScope
import su.tease.core.mvi.component.component.provider.PageProvider
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.root
import su.tease.project.core.utils.ext.alsoIf
import su.tease.project.core.utils.ext.ifTrue
import su.tease.project.core.utils.ext.removeIf

@Suppress("UNCHECKED_CAST")
class AppNavigationTargetResolver(
    private val scope: Scope,
    coroutineScope: CoroutineScope,
    pages: List<PageProvider<*>>,
    features: List<FeatureProvider<*>>,
    apps: List<AppProvider<*>>,
) : NavigationTargetResolver {

    private val store = scope.get<Store<*>>()

    private val mapPage = pages.associateBy { it.target }
    private val mapFeatures = features.associateBy { it.target }
    private val mapApps = apps.associateBy { it.target }

    private val pageCache = mutableMapOf<String, BasePageComponent<*>>()
    private val featureCache = mutableMapOf<String, BaseFeatureComponent<*>>()
    private val appCache = mutableMapOf<String, BaseAppComponent<*>>()

    init {
        coroutineScope.launch {
            store.select(this, root()).collect { root ->
                val pageIdSet = root.pageIdList.toSet()
                pageCache.removeIf { (it !in pageIdSet).ifTrue { pageCache[it]?.onFinish() } }

                val featureIdSet = root.featureIdList.toSet()
                featureCache.removeIf { it !in featureIdSet }

                val appIdSet = root.appIdList.toSet()
                appCache.removeIf { it !in appIdSet }
            }
        }
    }

    override fun <T : NavigationTarget.Page> resolve(
        pageId: String,
        page: T,
    ) = pageCache.getOrPut(pageId) {
        mapPage[page::class.java]
            ?.let { it as? PageProvider<T> }
            ?.run { NavigationScope<T>(scope, store, page).component() }
            ?: error("There are no page component")
    }

    override fun <T : NavigationTarget.Feature> resolve(
        featureId: String,
        feature: T,
        featureNavigation: FeatureNavigation,
    ) = featureCache.getOrPut(featureId) {
        mapFeatures[feature::class.java]
            ?.let { it as? FeatureProvider<T> }
            ?.run { NavigationScope<T>(scope, store, feature).component() }
            ?.also { it.featureNavigation = featureNavigation }
            ?: error("There are no feature component")
    }

    override fun <T : NavigationTarget.App> resolve(
        appId: String,
        app: T,
        appNavigation: AppNavigation,
    ) = appCache.getOrPut(appId) {
        mapApps[app::class.java]
            ?.let { it as? AppProvider<T> }
            ?.run { NavigationScope<T>(scope, store, app).component() }
            ?.also { it.appNavigation = appNavigation }
            ?: error("There are no app component")
    }
}
