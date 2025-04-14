package su.tease.project.core.mvi_navigation.state

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.core.mvi.navigation.RootNavigation

@Parcelize
data object AppNavigationTarget : NavigationTarget.App

@Parcelize
data object MainFeatureNavigationTarget : NavigationTarget.Feature

@Parcelize
data object SplashNavigationTarget : NavigationTarget.Page

@Parcelize
data object FinishNavigationTarget : NavigationTarget.Page

val baseRootNavigation: RootNavigation = RootNavigation(
    initApp = AppNavigation(
        name = AppNavigationTarget,
        initFeature = FeatureNavigation(
            name = MainFeatureNavigationTarget,
            initPage = PageNavigation(
                name = SplashNavigationTarget
            )
        )
    )
)

val finishRootNavigationTarget = RootNavigation(
    initApp = AppNavigation(
        name = AppNavigationTarget,
        initFeature = FeatureNavigation(
            name = MainFeatureNavigationTarget,
            initPage = PageNavigation(
                name = FinishNavigationTarget
            )
        )
    )
)
