package su.tease.feature.splash

import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.state.AppNavigationTarget
import su.tease.project.core.mvi_navigation.state.MainFeatureNavigationTarget
import su.tease.project.core.mvi_navigation.state.SplashNavigationTarget

class SplashNavigationTargetResolver<S : State>(
    private val store: Store<S>
) : NavigationTargetResolver {

    override fun resolve(page: NavigationTarget.Page): BasePageComponent<*>? = when (page) {
        SplashNavigationTarget -> SplashPageComponent(store)
        else -> null
    }

    override fun resolve(feature: NavigationTarget.Feature): BaseFeatureComponent<*>? = when (feature) {
        MainFeatureNavigationTarget -> SplashFeatureComponent(store)
        else -> null
    }

    override fun resolve(app: NavigationTarget.App): BaseAppComponent<*>? = when (app) {
        AppNavigationTarget -> SplashAppComponent(store)
        else -> null
    }
}