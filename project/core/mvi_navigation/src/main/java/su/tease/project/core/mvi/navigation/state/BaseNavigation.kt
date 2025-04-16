package su.tease.project.core.mvi.navigation.state

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.RootNavigation
import su.tease.core.mvi.navigation.app

@Parcelize
data object AppNavigationTarget : NavigationTarget.App

@Parcelize
data object MainFeatureNavigationTarget : NavigationTarget.Feature

@Parcelize
data object SplashNavigationTarget : NavigationTarget.Page

@Parcelize
data object FinishNavigationTarget : NavigationTarget.Page

val baseRootNavigation: RootNavigation = RootNavigation(
    initApp = app(
        AppNavigationTarget,
        FeatureNavigation(
            name = MainFeatureNavigationTarget,
            initPage = SplashNavigationTarget,
        ),
    ),
)

val finishRootNavigationTarget = RootNavigation(
    initApp = app(
        AppNavigationTarget,
        FeatureNavigation(
            name = MainFeatureNavigationTarget,
            initPage = FinishNavigationTarget,
        ),
    ),
)
