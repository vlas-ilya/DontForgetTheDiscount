package su.tease.project.feature.cacheback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.cacheback.data.dataSource.DictionaryDataSource
import su.tease.project.feature.cacheback.data.repository.CacheBackBankRepositoryImpl
import su.tease.project.feature.cacheback.data.repository.DictionaryRepositoryImpl
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.impl.DictionaryInterceptorImpl
import su.tease.project.feature.cacheback.domain.repository.CacheBackBankRepository
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListUseCase
import su.tease.project.feature.cacheback.domain.usecase.impl.LoadCacheBackBankListUseCaseImpl
import su.tease.project.feature.cacheback.presentation.list.CacheBackListFeature
import su.tease.project.feature.cacheback.presentation.list.CacheBackListPage

val cacheBackModule = module {

    api<DictionaryDataSource>()

    factory<DictionaryRepository> { DictionaryRepositoryImpl(get(), get()) }
    factory<DictionaryInterceptor> { DictionaryInterceptorImpl(get()) }
    factory<CacheBackBankRepository> { CacheBackBankRepositoryImpl(get(), get(), get()) }
    factory<LoadCacheBackBankListUseCase> { LoadCacheBackBankListUseCaseImpl(get()) }

    feature<CacheBackListFeature.Target> { CacheBackListFeature(it.store) }

    page<CacheBackListPage.Target> { CacheBackListPage(it.store, get(),) }
}
