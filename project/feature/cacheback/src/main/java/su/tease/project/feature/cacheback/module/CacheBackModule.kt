package su.tease.project.feature.cacheback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.cacheback.data.dataSource.Dictionary
import su.tease.project.feature.cacheback.domain.interceptor.CacheBackBankInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.impl.CacheBackBankInterceptorImpl
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListUseCase
import su.tease.project.feature.cacheback.domain.usecase.impl.LoadCacheBackBankListUseCaseImpl
import su.tease.project.feature.cacheback.presentation.list.CacheBackListFeature
import su.tease.project.feature.cacheback.presentation.list.CacheBackListPage

val cacheBackModule = module {

    api<Dictionary>()

    factory<CacheBackBankInterceptor> {
        CacheBackBankInterceptorImpl()
    }

    factory<LoadCacheBackBankListUseCase> {
        LoadCacheBackBankListUseCaseImpl()
    }

    feature<CacheBackListFeature.Target> {
        CacheBackListFeature(store = it.store)
    }

    page<CacheBackListPage.Target> {
        CacheBackListPage(
            store = it.store,
            loadCacheBackBankListUseCase = get(),
        )
    }
}
