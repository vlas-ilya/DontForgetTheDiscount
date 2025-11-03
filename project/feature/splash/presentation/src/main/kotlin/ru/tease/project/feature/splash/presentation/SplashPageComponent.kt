package ru.tease.project.feature.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.tease.project.feature.splash.domain.AppInitializationOnSplashUseCase
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.SplashNavigationTarget

class SplashPageComponent(
    store: Store<*>,
    private val appInitializationOnSplashUseCase: AppInitializationOnSplashUseCase,
    private val splashNavigation: SplashNavigation,
) : BasePageComponent<SplashNavigationTarget>(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            appInitializationOnSplashUseCase.onInit()
            splashNavigation.target.replace()
        }
    }
}
