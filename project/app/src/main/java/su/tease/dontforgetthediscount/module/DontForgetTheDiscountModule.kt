package su.tease.dontforgetthediscount.module

import org.koin.dsl.module
import retrofit2.Retrofit
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.dontforgetthediscount.network.retrofit
import su.tease.project.core.mvi.api.store.Store

val dontForgetTheDiscountModule = module {

    single<Retrofit> { retrofit("https://dontforgetthediscount.ru/") }

    factory<DontForgetTheDiscountComponent<*>> {
        DontForgetTheDiscountComponent(
            store = get<Store<*>>(),
            navigationTargetResolver = get(),
        )
    }

    single<AppNavigationTargetResolver> {
        AppNavigationTargetResolver(
            scope = this,
            pages = getAll(),
            features = getAll(),
            apps = getAll(),
        )
    }
}
