package su.tease.dontforgetthediscount.module

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.core.utils.uuid.impl.UuidProviderImpl

val dontForgetTheDiscountModule = module {

    factory {
        DontForgetTheDiscountComponent(
            store = get<Store<*>>(),
            navigationTargetResolver = get(),
        )
    }

    single {
        AppNavigationTargetResolver(
            scope = this,
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
            pages = getAll(),
            features = getAll(),
            apps = getAll(),
        )
    }

    single { SimpleCache() }

    factory<UuidProvider> { UuidProviderImpl() }
}
