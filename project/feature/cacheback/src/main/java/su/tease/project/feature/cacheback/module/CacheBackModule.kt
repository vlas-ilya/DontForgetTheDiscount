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
import su.tease.project.feature.cacheback.domain.usecase.AddBankUseCase
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCase
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackUseCase
import su.tease.project.feature.cacheback.domain.usecase.impl.AddBankUseCaseImpl
import su.tease.project.feature.cacheback.domain.usecase.impl.AddCodeUseCaseImpl
import su.tease.project.feature.cacheback.domain.usecase.impl.LoadBankListUseCaseImpl
import su.tease.project.feature.cacheback.domain.usecase.impl.SaveCacheBackUseCaseImpl
import su.tease.project.feature.cacheback.presentation.CacheBackFeature
import su.tease.project.feature.cacheback.presentation.list.ListCacheBackPage
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackPage
import su.tease.project.feature.cacheback.presentation.select.bank.AddBankPage
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage

val cacheBackModule = module {
    api<DictionaryDataSource>()

    factory<DictionaryRepository> { DictionaryRepositoryImpl(get(), get(), get()) }
    factory<DictionaryInterceptor> { DictionaryInterceptorImpl(get()) }
    factory<BankRepository> { BankRepositoryImpl(get(), get(), get()) }
    factory<LoadBankListUseCase> { LoadBankListUseCaseImpl(get(), get()) }
    factory<SaveCacheBackUseCase> { SaveCacheBackUseCaseImpl(get(), get()) }
    factory<AddBankUseCase> { AddBankUseCaseImpl(get(), get()) }
    factory<AddCodeUseCase> { AddCodeUseCaseImpl(get(), get()) }

    feature<CacheBackFeature.Target> { CacheBackFeature(it.store) }
    feature<SaveCacheBackFeature.Target> { SaveCacheBackFeature(it.store) }

    page<ListCacheBackPage.Target> { ListCacheBackPage(it.store, get(), get(), get()) }
    page<SaveCacheBackPage.Target> { SaveCacheBackPage(it.store, it.target, get(), get()) }
    page<BankSelectPage.Target> { BankSelectPage(it.store, it.target, get()) }
    page<CodesSelectPage.Target> { CodesSelectPage(it.store, it.target, get(), get()) }
    page<IconSelectPage.Target> { IconSelectPage(it.store, it.target, get()) }
    page<AddBankPage.Target> { AddBankPage(it.store, get()) }
}
