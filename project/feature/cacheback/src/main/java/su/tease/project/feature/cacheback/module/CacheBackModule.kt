package su.tease.project.feature.cacheback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.cacheback.data.repository.BankAccountRepositoryImpl
import su.tease.project.feature.cacheback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.impl.BankAccountInterceptorImpl
import su.tease.project.feature.cacheback.domain.repository.BankAccountRepository
import su.tease.project.feature.cacheback.presentation.CacheBackFeature
import su.tease.project.feature.cacheback.presentation.OtherFeature
import su.tease.project.feature.cacheback.presentation.ShopFeature
import su.tease.project.feature.cacheback.presentation.list.ListCacheBackPage
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cacheback.presentation.list.action.impl.LoadBankAccountListActionImpl
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
import su.tease.project.feature.cacheback.presentation.save.bank.save.SaveBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.SaveBankAccountAction
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.impl.SaveBankAccountActionImpl
import su.tease.project.feature.cacheback.presentation.save.bank.select.SelectBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.SaveCacheBackPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackAction
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.impl.SaveCacheBackActionImpl

val cacheBackModule = module {
    factory<BankAccountRepository> { BankAccountRepositoryImpl(get(), get(), get()) }

    factory<BankAccountInterceptor> { BankAccountInterceptorImpl(get()) }

    factory<LoadBankAccountListAction> { LoadBankAccountListActionImpl(get(), get()) }
    factory<SaveCacheBackAction> { SaveCacheBackActionImpl(get(), get(), get(), get()) }
    factory<SaveBankAccountAction> { SaveBankAccountActionImpl(get(), get(), get()) }

    feature { CacheBackFeature(get()) }
    feature { ShopFeature(get()) }
    feature { OtherFeature(get()) }
    feature { SaveCacheBackFeature(get()) }

    page { ListCacheBackPage(get(), get(), get(), get()) }
    page { SaveCacheBackPage(get(), get(), get(), get()) }
    page { SelectBankAccountPage(get(), get(), get()) }
    page { SaveBankAccountPage(get(), get(), get()) }
}
