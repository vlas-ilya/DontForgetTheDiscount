package su.tease.project.feature.main.module

import org.koin.dsl.bind
import org.koin.dsl.module
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.project.core.mvi.api.store.Store

val mainModule = module {
    factory {
        MainNavigationTargetResolver(store = get<Store<*>>())
    } bind NavigationTargetResolver::class
}
