package su.tease.feature.splash.integration.presentation

import ru.tease.project.feature.splash.presentation.SplashNavigation
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.project.feature.main.presentation.MainApp

class SplashNavigationImpl : SplashNavigation {
    override val target: AppNavigation = MainApp()
}