package su.tease.feature.splash

import org.koin.dsl.module
import su.tease.core.component.component.provider.app
import su.tease.core.component.component.provider.feature
import su.tease.core.component.component.provider.page
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.state.AppNavigationTarget
import su.tease.project.core.mvi_navigation.state.MainFeatureNavigationTarget
import su.tease.project.core.mvi_navigation.state.SplashNavigationTarget

val splashModule = module {
    page<SplashNavigationTarget> { SplashPageComponent(store = get<Store<*>>()) }
    feature<MainFeatureNavigationTarget> { SplashFeatureComponent(store = get<Store<*>>()) }
    app<AppNavigationTarget> { SplashAppComponent(store = get<Store<*>>()) }
}
