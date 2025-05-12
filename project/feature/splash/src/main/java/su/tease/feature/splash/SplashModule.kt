package su.tease.feature.splash

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.app
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page

val splashModule = module {
    page { SplashPageComponent(get(), get()) }
    feature { SplashFeatureComponent(get()) }
    app { SplashAppComponent(get()) }
}
