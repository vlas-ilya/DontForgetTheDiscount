package su.tease.dontforgetthediscount.module

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.dontforgetthediscount.component.DontForgetTheDiscountComponent
import su.tease.dontforgetthediscount.init.AppInitializationOnSplashUseCaseImpl
import su.tease.feature.splash.AppInitializationOnSplashUseCase
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.date.impl.DateProviderImpl
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.resource.impl.ResourceProviderImpl
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.core.utils.uuid.impl.UuidProviderImpl
import java.util.Calendar

val dontForgetTheDiscountModule = module {

    factory { params ->
        DontForgetTheDiscountComponent(
            store = get<Store<*>>(),
            navigationTargetResolver = get(),
            windowProvider = params.get(),
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

    factory<ResourceProvider> { ResourceProviderImpl(get()) }

    factory<DateProvider> { DateProviderImpl(get(), Calendar.getInstance()) }

    factory<AppInitializationOnSplashUseCase> { AppInitializationOnSplashUseCaseImpl(get()) }
}
