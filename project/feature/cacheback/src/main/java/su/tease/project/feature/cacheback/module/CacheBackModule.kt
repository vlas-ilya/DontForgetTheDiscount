package su.tease.project.feature.cacheback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.cacheback.data.dataSource.PresetDataSource
import su.tease.project.feature.cacheback.data.repository.BankAccountRepositoryImpl
import su.tease.project.feature.cacheback.data.repository.PresetRepositoryImpl
import su.tease.project.feature.cacheback.data.repository.SyncPresetRepositoryImpl
import su.tease.project.feature.cacheback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.PresetInterceptor
import su.tease.project.feature.cacheback.domain.interceptor.impl.BankAccountInterceptorImpl
import su.tease.project.feature.cacheback.domain.interceptor.impl.PresetInterceptorImpl
import su.tease.project.feature.cacheback.domain.repository.BankAccountRepository
import su.tease.project.feature.cacheback.domain.repository.PresetRepository
import su.tease.project.feature.cacheback.domain.repository.SyncPresetRepository
import su.tease.project.feature.cacheback.presentation.CacheBackFeature
import su.tease.project.feature.cacheback.presentation.list.ListCacheBackPage
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cacheback.presentation.list.action.impl.LoadBankAccountListActionImpl
import su.tease.project.feature.cacheback.presentation.preset.bank.save.SaveBankPresetPage
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.impl.SaveBankPresetActionImpl
import su.tease.project.feature.cacheback.presentation.preset.bank.select.SelectBankPresetPage
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.SaveCacheBackPresetPage
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.action.SaveCacheBackPresetAction
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.action.impl.SaveCacheBackPresetActionImpl
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.SelectCacheBackPresetPage
import su.tease.project.feature.cacheback.presentation.preset.icon.select.IconSelectPresetPage
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.impl.SelectMccCodeActionImpl
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
import su.tease.project.feature.cacheback.presentation.save.bank.save.SaveBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.SaveBankAccountAction
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.impl.SaveBankAccountActionImpl
import su.tease.project.feature.cacheback.presentation.save.bank.select.SelectBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.SaveCacheBackPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackAction
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.impl.SaveCacheBackActionImpl

val cacheBackModule = module {
    api<PresetDataSource>()

    factory<PresetRepository> { PresetRepositoryImpl(get(), get(), get()) }
    factory<BankAccountRepository> { BankAccountRepositoryImpl(get(), get(), get()) }
    factory<SyncPresetRepository> { SyncPresetRepositoryImpl(get(), get(), get()) }

    factory<PresetInterceptor> { PresetInterceptorImpl(get()) }
    factory<BankAccountInterceptor> { BankAccountInterceptorImpl(get()) }

    factory<LoadBankAccountListAction> { LoadBankAccountListActionImpl(get(), get()) }
    factory<SaveCacheBackAction> { SaveCacheBackActionImpl(get(), get(), get(), get()) }
    factory<SaveBankPresetAction> { SaveBankPresetActionImpl(get(), get()) }
    factory<SaveBankAccountAction> { SaveBankAccountActionImpl(get(), get(), get()) }
    factory<SaveCacheBackPresetAction> { SaveCacheBackPresetActionImpl(get(), get()) }
    factory<SelectMccCodeAction> { SelectMccCodeActionImpl(get(), get()) }

    feature { CacheBackFeature(get()) }
    feature { SaveCacheBackFeature(get()) }

    page { ListCacheBackPage(get(), get(), get(), get()) }
    page { SaveCacheBackPage(get(), get(), get(), get()) }
    page { SelectCacheBackPresetPage(get(), get(), get()) }
    page { SaveCacheBackPresetPage(get(), get(), get()) }
    page { SelectBankPresetPage(get(), get(), get()) }
    page { SaveBankPresetPage(get(), get()) }
    page { SelectBankAccountPage(get(), get(), get()) }
    page { SaveBankAccountPage(get(), get(), get()) }
    page { SelectMccCodePresetPage(get(), get(), get(), get()) }
    page { IconSelectPresetPage(get(), get(), get()) }
}
