package su.tease.project.feature.cacheback.module

import org.koin.dsl.bind
import org.koin.dsl.module
import su.tease.core.component.component.provider.feature
import su.tease.core.component.component.provider.page
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.usecase.ListCacheBackUseCase
import su.tease.project.feature.cacheback.domain.usecase.impl.ListCacheBackUseCaseImpl
import su.tease.project.feature.cacheback.presentation.CacheBackListFeature
import su.tease.project.feature.cacheback.presentation.CacheBackListPage

val cacheBackModule = module {
    factory { ListCacheBackUseCaseImpl() } bind ListCacheBackUseCase::class

    feature<CacheBackListFeature.Target> {
        CacheBackListFeature(
            store = get<Store<*>>(),
            listCacheBackUseCase = get()
        )
    }

    page<CacheBackListPage.Target> {
        CacheBackListPage(
            store = get<Store<*>>(),
        )
    }
}
