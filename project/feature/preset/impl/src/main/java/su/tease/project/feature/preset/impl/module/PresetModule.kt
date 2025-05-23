package su.tease.project.feature.preset.impl.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.api.domain.interceptor.SyncPresetInterceptor
import su.tease.project.feature.preset.impl.domain.repository.SyncPresetRepository
import su.tease.project.feature.preset.impl.data.dataSource.PresetDataSource
import su.tease.project.feature.preset.impl.data.repository.PresetRepositoryImpl
import su.tease.project.feature.preset.impl.data.repository.SyncPresetRepositoryImpl
import su.tease.project.feature.preset.impl.domain.interseptor.PresetInterceptorImpl
import su.tease.project.feature.preset.impl.domain.interseptor.SyncPresetInterceptorImpl
import su.tease.project.feature.preset.impl.domain.repository.PresetRepository
import su.tease.project.feature.preset.impl.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.impl.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.impl.presentation.bank.save.action.impl.SaveBankPresetActionImpl
import su.tease.project.feature.preset.impl.presentation.bank.select.SelectBankPresetPageImpl
import su.tease.project.feature.preset.impl.presentation.cacheback.save.SaveCacheBackPresetPage
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetAction
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.impl.SaveCacheBackPresetActionImpl
import su.tease.project.feature.preset.impl.presentation.cacheback.select.SelectCacheBackPresetPageImpl
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.preset.impl.presentation.mcc.select.action.impl.SelectMccCodeActionImpl

val presetModule = module {
    api<PresetDataSource>()

    factory<PresetRepository> { PresetRepositoryImpl(get(), get()) }
    factory<SyncPresetRepository> { SyncPresetRepositoryImpl(get(), get(), get()) }

    factory<SyncPresetInterceptor> { SyncPresetInterceptorImpl(get()) }
    factory<PresetInterceptor> { PresetInterceptorImpl(get()) }

    factory<SaveBankPresetAction> { SaveBankPresetActionImpl(get(), get()) }
    factory<SaveCacheBackPresetAction> { SaveCacheBackPresetActionImpl(get(), get()) }
    factory<SelectMccCodeAction> { SelectMccCodeActionImpl(get(), get()) }

    page { SelectCacheBackPresetPageImpl(get(), get(), get()) }
    page { SaveCacheBackPresetPage(get(), get(), get()) }
    page { SelectBankPresetPageImpl(get(), get(), get()) }
    page { SaveBankPresetPage(get(), get()) }
    page { SelectMccCodePresetPage(get(), get(), get(), get()) }
    page { IconSelectPresetPage(get(), get(), get()) }
}
