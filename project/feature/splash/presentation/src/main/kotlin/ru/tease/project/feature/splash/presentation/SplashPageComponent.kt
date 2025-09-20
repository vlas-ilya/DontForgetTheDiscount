package ru.tease.project.feature.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.tease.project.feature.splash.domain.AppInitializationOnSplashUseCase
import ru.tease.project.feature.splash.presentation.dependencies.navigation.MainPage
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.SplashNavigationTarget

class SplashPageComponent(
    store: Store<*>,
    private val appInitializationOnSplashUseCase: AppInitializationOnSplashUseCase
) : BasePageComponent<SplashNavigationTarget>(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            appInitializationOnSplashUseCase.onInit()
            MainPage.forward()
        }
    }
}
