package su.tease.project.feature.cacheback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.cacheback.data.dataSource.DictionaryDataSource
import su.tease.project.feature.cacheback.data.repository.BankRepositoryImpl
import su.tease.project.feature.cacheback.data.repository.DictionaryRepositoryImpl
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.impl.DictionaryInterceptorImpl
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCase
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.domain.usecase.impl.AddCacheBackUseCaseImpl
import su.tease.project.feature.cacheback.domain.usecase.impl.AddCodeUseCaseImpl
import su.tease.project.feature.cacheback.domain.usecase.impl.LoadBankListUseCaseImpl
import su.tease.project.feature.cacheback.presentation.CacheBackFeature
import su.tease.project.feature.cacheback.presentation.add.AddCacheBackFeature
import su.tease.project.feature.cacheback.presentation.add.CacheBackAddPage
import su.tease.project.feature.cacheback.presentation.list.CacheBackListPage
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage

val cacheBackModule = module {

    api<DictionaryDataSource>()

    factory<DictionaryRepository> { DictionaryRepositoryImpl(get(), get(), get()) }
    factory<DictionaryInterceptor> { DictionaryInterceptorImpl(get()) }
    factory<BankRepository> { BankRepositoryImpl(get(), get(), get()) }
    factory<LoadBankListUseCase> { LoadBankListUseCaseImpl(get()) }
    factory<AddCacheBackUseCase> { AddCacheBackUseCaseImpl(get(), get()) }
    factory<AddCodeUseCase> { AddCodeUseCaseImpl(get(), get()) }

    feature<CacheBackFeature.Target> { CacheBackFeature(it.store) }
    feature<AddCacheBackFeature.Target> { AddCacheBackFeature(it.store) }

    page<CacheBackListPage.Target> { CacheBackListPage(it.store, get()) }
    page<CacheBackAddPage.Target> { CacheBackAddPage(it.store, get()) }
    page<BankSelectPage.Target> { BankSelectPage(it.store, it.target, get()) }
    page<CodesSelectPage.Target> { CodesSelectPage(it.store, it.target, get(), get()) }
    page<IconSelectPage.Target> { IconSelectPage(it.store, it.target, get()) }
}
