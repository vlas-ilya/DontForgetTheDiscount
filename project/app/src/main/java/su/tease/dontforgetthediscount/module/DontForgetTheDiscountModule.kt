package su.tease.dontforgetthediscount.module

import org.koin.dsl.module
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.project.core.mvi.api.store.Store

val dontForgetTheDiscountModule = module {
    factory<DontForgetTheDiscountComponent<*>> {
        DontForgetTheDiscountComponent(
            store = get<Store<*>>(),
            navigationTargetResolver = get(),
        )
    }

    factory<AppNavigationTargetResolver> {
        AppNavigationTargetResolver(
            scope = this,
            pages = getAll(),
            features = getAll(),
            apps = getAll(),
        )
    }
}
