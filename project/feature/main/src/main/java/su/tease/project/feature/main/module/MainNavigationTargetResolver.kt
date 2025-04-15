package su.tease.project.feature.main.module

import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.state.SplashNavigationTarget
import su.tease.project.feature.main.presentation.MainApp1
import su.tease.project.feature.main.presentation.MainApp1NavigationTarget
import su.tease.project.feature.main.presentation.MainApp2
import su.tease.project.feature.main.presentation.MainApp2NavigationTarget
import su.tease.project.feature.main.presentation.MainFeature1
import su.tease.project.feature.main.presentation.MainFeature1NavigationTarget
import su.tease.project.feature.main.presentation.MainFeature2
import su.tease.project.feature.main.presentation.MainFeature2NavigationTarget
import su.tease.project.feature.main.presentation.MainFeature3
import su.tease.project.feature.main.presentation.MainFeature3NavigationTarget
import su.tease.project.feature.main.presentation.MainPage1
import su.tease.project.feature.main.presentation.MainPage1NavigationTarget
import su.tease.project.feature.main.presentation.MainPage2
import su.tease.project.feature.main.presentation.MainPage2NavigationTarget
import su.tease.project.feature.main.presentation.MainPage3
import su.tease.project.feature.main.presentation.MainPage3NavigationTarget
import su.tease.project.feature.main.presentation.MainPage4
import su.tease.project.feature.main.presentation.MainPage4NavigationTarget

class MainNavigationTargetResolver<S : State>(
    private val store: Store<S>
) : NavigationTargetResolver {

    override fun resolve(page: NavigationTarget.Page) = when (page) {
        is MainPage1NavigationTarget -> MainPage1(store)
        is MainPage2NavigationTarget -> MainPage2(store)
        is MainPage3NavigationTarget -> MainPage3(store)
        is MainPage4NavigationTarget -> MainPage4(store)
        else -> null
    }

    override fun resolve(feature: NavigationTarget.Feature) = when (feature) {
        is MainFeature1NavigationTarget -> MainFeature1(store)
        is MainFeature2NavigationTarget -> MainFeature2(store)
        is MainFeature3NavigationTarget -> MainFeature3(store)
        else -> null
    }

    override fun resolve(app: NavigationTarget.App) = when (app) {
        is MainApp1NavigationTarget -> MainApp1(store)
        is MainApp2NavigationTarget -> MainApp2(store)
        else -> null
    }
}
