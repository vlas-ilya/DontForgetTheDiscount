package su.tease.feature.splash

import org.koin.dsl.bind
import org.koin.dsl.module
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.project.core.mvi.api.store.Store

val splashModule = module {
    factory {
        SplashNavigationTargetResolver(store = get<Store<*>>())
    } bind NavigationTargetResolver::class
}
