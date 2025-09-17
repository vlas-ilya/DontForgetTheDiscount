package su.tease.project.feature.preset.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.core.utils.utils.api
import su.tease.project.feature.preset.data.dataSource.PresetDataSource
import su.tease.project.feature.preset.data.repository.PresetRepositoryImpl
import su.tease.project.feature.preset.data.repository.SyncPresetRepositoryImpl
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.domain.interactor.SyncPresetInteractor
import su.tease.project.feature.preset.domain.interactor.impl.PresetInteractorImpl
import su.tease.project.feature.preset.domain.interactor.impl.SyncPresetInteractorImpl
import su.tease.project.feature.preset.domain.repository.PresetRepository
import su.tease.project.feature.preset.domain.repository.SyncPresetRepository
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.impl.SaveBankPresetActionImpl
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.cashback.save.SaveCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.presentation.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.preset.presentation.mcc.select.action.impl.SelectMccCodeActionImpl

val presetIntegrationModule = module {
    api<PresetDataSource>()

    factory<PresetRepository> { PresetRepositoryImpl(get(), get()) }
    factory<SyncPresetRepository> { SyncPresetRepositoryImpl(get(), get(), get()) }
    factory<PresetInteractor> { PresetInteractorImpl(get()) }
    factory<SyncPresetInteractor> { SyncPresetInteractorImpl(get()) }
    factory<SaveBankPresetAction> { SaveBankPresetActionImpl(get(), get()) }
    factory<SaveCashBackPresetAction> { SaveCashBackPresetActionImpl(get(), get()) }
    factory<SelectMccCodeAction> { SelectMccCodeActionImpl(get(), get()) }

    page { SelectCashBackPresetPage(get(), get(), get()) }
    page { SaveCashBackPresetPage(get(), get(), get()) }
    page { SelectBankPresetPage(get(), get(), get()) }
    page { SaveBankPresetPage(get(), get()) }
    page { SelectMccCodePresetPage(get(), get(), get(), get()) }
    page { SelectIconPresetPage(get(), get(), get()) }
}
