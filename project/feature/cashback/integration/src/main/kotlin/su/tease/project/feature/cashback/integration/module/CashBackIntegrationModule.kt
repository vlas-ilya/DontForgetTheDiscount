package su.tease.project.feature.cashback.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.cashback.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.cashback.data.repository.CashBackListRepositoryImpl
import su.tease.project.feature.cashback.data.repository.CashBackOwnerRepositoryImpl
import su.tease.project.feature.cashback.data.repository.PresetRepositoryImpl
import su.tease.project.feature.cashback.domain.interactor.CashBackListInteractor
import su.tease.project.feature.cashback.domain.interactor.impl.CashBackListInteractorImpl
import su.tease.project.feature.cashback.domain.repository.CashBackListRepository
import su.tease.project.feature.cashback.domain.repository.CashBackOwnerRepository
import su.tease.project.feature.cashback.domain.repository.PresetRepository
import su.tease.project.feature.cashback.domain.usecase.SaveCashBackUseCase
import su.tease.project.feature.cashback.integration.dependencies.data.PresetIntegrationInteractorImpl
import su.tease.project.feature.cashback.integration.dependencies.presentation.view.CashBackPresetIconViewImpl
import su.tease.project.feature.cashback.presentation.SaveCashBackFeature
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.cashback.presentation.save.SaveCashBackPage
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.action.impl.SaveCashBackActionImpl

val cashBackIntegrationModule = module {
    factory<PresetIntegrationInteractor> { PresetIntegrationInteractorImpl(get()) }
    factory<CashBackOwnerRepository> { CashBackOwnerRepositoryImpl(get()) }
    factory<PresetRepository> { PresetRepositoryImpl(get()) }
    factory<CashBackListRepository> { CashBackListRepositoryImpl(get(), get()) }
    factory<CashBackListInteractor> { CashBackListInteractorImpl(get()) }
    factory<SaveCashBackAction> { SaveCashBackActionImpl(get(), get(), get()) }
    factory<CashBackPresetIconView> { CashBackPresetIconViewImpl() }
    factory { SaveCashBackUseCase(get(), get(), get()) }
    feature { SaveCashBackFeature(get()) }
    page { SaveCashBackPage(get(), get(), get(), get(), get(), get(), get()) }
}
