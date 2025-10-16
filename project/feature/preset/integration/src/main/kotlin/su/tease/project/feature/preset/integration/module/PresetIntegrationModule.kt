package su.tease.project.feature.preset.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
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
import su.tease.project.feature.preset.presentation.bank.info.list.ListBankPresetPage
import su.tease.project.feature.preset.presentation.bank.info.save.SaveBankPresetFeature
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.impl.SaveBankPresetActionImpl
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.cashback.info.list.ListInfoCashbackPresetPage
import su.tease.project.feature.preset.presentation.cashback.info.save.SaveCashBackPresetFeature
import su.tease.project.feature.preset.presentation.cashback.save.SaveCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.icon.info.ListIconPresetPage
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.presentation.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.preset.presentation.mcc.select.action.impl.SelectMccCodeActionImpl
import su.tease.project.feature.preset.presentation.shop.info.list.ListShopPresetPage
import su.tease.project.feature.preset.presentation.shop.info.save.SaveShopPresetFeature
import su.tease.project.feature.preset.presentation.shop.save.SaveShopPresetPage
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetAction
import su.tease.project.feature.preset.presentation.shop.save.action.impl.SaveShopPresetActionImpl
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage

val presetIntegrationModule = module {
    api<PresetDataSource>()

    factory<PresetRepository> { PresetRepositoryImpl(get(), get()) }
    factory<SyncPresetRepository> { SyncPresetRepositoryImpl(get(), get(), get()) }
    factory<PresetInteractor> { PresetInteractorImpl(get()) }
    factory<SyncPresetInteractor> { SyncPresetInteractorImpl(get()) }
    factory<SaveBankPresetAction> { SaveBankPresetActionImpl(get(), get(), get()) }
    factory<SaveShopPresetAction> { SaveShopPresetActionImpl(get(), get(), get()) }
    factory<SaveCashBackPresetAction> { SaveCashBackPresetActionImpl(get(), get(), get()) }
    factory<SelectMccCodeAction> { SelectMccCodeActionImpl(get(), get()) }

    feature { SaveCashBackPresetFeature(get()) }
    page { ListInfoCashbackPresetPage(get(), get(), get()) }
    page { SelectCashBackPresetPage(get(), get(), get(), get()) }
    page { SaveCashBackPresetPage(get(), get(), get()) }

    feature { SaveBankPresetFeature(get()) }
    page { ListBankPresetPage(get(), get(), get()) }
    page { SelectBankPresetPage(get(), get(), get(), get()) }
    page { SaveBankPresetPage(get(), get(), get()) }

    feature { SaveShopPresetFeature(get()) }
    page { ListShopPresetPage(get(), get(), get()) }
    page { SelectShopPresetPage(get(), get(), get(), get()) }
    page { SaveShopPresetPage(get(), get(), get()) }

    page { ListIconPresetPage(get(), get(), get(), get()) }
    page { SelectIconPresetPage(get(), get(), get(), get()) }

    page { SelectMccCodePresetPage(get(), get(), get(), get()) }
}
