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
import su.tease.project.feature.preset.integration.icon.save.SaveIconPresetActionImpl
import su.tease.project.feature.preset.presentation.bank.info.list.ListBankPresetPage
import su.tease.project.feature.preset.presentation.bank.info.save.SaveBankPresetFeature
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.SelectBankIconAction
import su.tease.project.feature.preset.presentation.bank.save.action.impl.SaveBankPresetActionImpl
import su.tease.project.feature.preset.presentation.bank.save.action.impl.SelectBankIconActionImpl
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.action.CreateBankPresetAction
import su.tease.project.feature.preset.presentation.bank.select.action.impl.CreateBankPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.info.list.ListInfoCashbackPresetPage
import su.tease.project.feature.preset.presentation.cashback.info.save.SaveCashBackPresetFeature
import su.tease.project.feature.preset.presentation.cashback.save.SaveCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectBankPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectIconAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectMccCodeAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectShopPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackSelectBankPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackSelectIconActionImpl
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackSelectMccCodeActionImpl
import su.tease.project.feature.preset.presentation.cashback.save.action.impl.SaveCashBackSelectShopPresetActionImpl
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.select.action.CreateCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.select.action.impl.CreateCashBackPresetActionImpl
import su.tease.project.feature.preset.presentation.icon.info.list.ListIconPresetPage
import su.tease.project.feature.preset.presentation.icon.info.save.SaveIconPresetFeature
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetAction
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.icon.select.action.SelectIconPresetAction
import su.tease.project.feature.preset.presentation.icon.select.action.impl.SelectIconPresetActionImpl
import su.tease.project.feature.preset.presentation.mcc.info.list.ListMccCodePresetPage
import su.tease.project.feature.preset.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.presentation.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.preset.presentation.mcc.select.action.impl.SelectMccCodeActionImpl
import su.tease.project.feature.preset.presentation.shop.info.list.ListShopPresetPage
import su.tease.project.feature.preset.presentation.shop.info.save.SaveShopPresetFeature
import su.tease.project.feature.preset.presentation.shop.save.SaveShopPresetPage
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetAction
import su.tease.project.feature.preset.presentation.shop.save.action.SelectShopIconAction
import su.tease.project.feature.preset.presentation.shop.save.action.impl.SaveShopPresetActionImpl
import su.tease.project.feature.preset.presentation.shop.save.action.impl.SelectShopIconActionImpl
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage
import su.tease.project.feature.preset.presentation.shop.select.action.CreateShopPresetAction
import su.tease.project.feature.preset.presentation.shop.select.action.impl.CreateShopPresetActionImpl

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
    factory<SaveIconPresetAction> { SaveIconPresetActionImpl(get(), get(), get()) }
    factory<SelectIconPresetAction> { SelectIconPresetActionImpl() }
    factory<SelectBankIconAction> { SelectBankIconActionImpl() }
    factory<SelectShopIconAction> { SelectShopIconActionImpl() }
    factory<SaveCashBackSelectIconAction> { SaveCashBackSelectIconActionImpl() }
    factory<SaveCashBackSelectMccCodeAction> { SaveCashBackSelectMccCodeActionImpl() }
    factory<SaveCashBackSelectBankPresetAction> { SaveCashBackSelectBankPresetActionImpl() }
    factory<SaveCashBackSelectShopPresetAction> { SaveCashBackSelectShopPresetActionImpl() }
    factory<CreateBankPresetAction> { CreateBankPresetActionImpl() }
    factory<CreateShopPresetAction> { CreateShopPresetActionImpl() }
    factory<CreateCashBackPresetAction> { CreateCashBackPresetActionImpl() }

    feature { SaveCashBackPresetFeature(get()) }
    page { ListInfoCashbackPresetPage(get(), get(), get()) }
    page { SelectCashBackPresetPage(get(), get(), get(), get(), get()) }
    page { SaveCashBackPresetPage(get(), get(), get(), get(), get(), get(), get()) }

    feature { SaveBankPresetFeature(get()) }
    page { ListBankPresetPage(get(), get(), get()) }
    page { SelectBankPresetPage(get(), get(), get(), get()) }
    page { SaveBankPresetPage(get(), get(), get(), get()) }

    feature { SaveShopPresetFeature(get()) }
    page { ListShopPresetPage(get(), get(), get()) }
    page { SelectShopPresetPage(get(), get(), get(), get()) }
    page { SaveShopPresetPage(get(), get(), get(), get()) }

    feature { SaveIconPresetFeature(get()) }
    page { ListIconPresetPage(get(), get(), get(), get()) }
    page { SelectIconPresetPage(get(), get(), get(), get(), get()) }
    page { SaveIconPresetPage(get(), get(), get()) }

    page { ListMccCodePresetPage(get(), get(), get()) }
    page { SelectMccCodePresetPage(get(), get(), get(), get()) }
}
