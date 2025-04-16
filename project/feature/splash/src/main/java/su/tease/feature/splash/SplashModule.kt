package su.tease.feature.splash

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.app
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.AppNavigationTarget
import su.tease.project.core.mvi.navigation.state.MainFeatureNavigationTarget
import su.tease.project.core.mvi.navigation.state.SplashNavigationTarget

val splashModule = module {
    page<SplashNavigationTarget> { SplashPageComponent(store = get<Store<*>>()) }
    feature<MainFeatureNavigationTarget> { SplashFeatureComponent(store = get<Store<*>>()) }
    app<AppNavigationTarget> { SplashAppComponent(store = get<Store<*>>()) }
}
