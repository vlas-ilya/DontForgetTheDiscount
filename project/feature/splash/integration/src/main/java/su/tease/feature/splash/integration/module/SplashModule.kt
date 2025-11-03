package su.tease.feature.splash.integration.module

import org.koin.dsl.module
import ru.tease.project.feature.splash.domain.AppInitializationOnSplashUseCase
import ru.tease.project.feature.splash.presentation.SplashAppComponent
import ru.tease.project.feature.splash.presentation.SplashFeatureComponent
import ru.tease.project.feature.splash.presentation.SplashNavigation
import ru.tease.project.feature.splash.presentation.SplashPageComponent
import su.tease.core.mvi.component.component.provider.app
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.feature.splash.integration.domain.AppInitializationOnSplashUseCaseImpl
import su.tease.feature.splash.integration.presentation.SplashNavigationImpl

val splashModule = module {
    factory<SplashNavigation> { SplashNavigationImpl() }
    factory<AppInitializationOnSplashUseCase> { AppInitializationOnSplashUseCaseImpl(get()) }

    page { SplashPageComponent(get(), get(), get()) }
    feature { SplashFeatureComponent(get()) }
    app { SplashAppComponent(get()) }
}
