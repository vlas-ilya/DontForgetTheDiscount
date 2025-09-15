package su.tease.project.feature.bank.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.bank.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.bank.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.bank.data.repository.BankAccountRepositoryImpl
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.domain.interactor.impl.BankAccountInterceptorImpl
import su.tease.project.feature.bank.domain.repository.BankAccountRepository
import su.tease.project.feature.bank.integration.dependencies.data.CashBackIntegrationInteractorImpl
import su.tease.project.feature.bank.integration.dependencies.data.PresetIntegrationIntegrationInteractorImpl
import su.tease.project.feature.bank.integration.dependencies.presentation.view.BankPresetIconViewImpl
import su.tease.project.feature.bank.integration.dependencies.presentation.view.CashBackInfoDialogViewImpl
import su.tease.project.feature.bank.integration.dependencies.presentation.view.CashBackPresetIconViewImpl
import su.tease.project.feature.bank.presentation.BankAccountFeature
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.bank.presentation.list.BankAccountsPage
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsAction
import su.tease.project.feature.bank.presentation.list.action.impl.LoadBankAccountsActionImpl
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountAction
import su.tease.project.feature.bank.presentation.save.action.impl.SaveBankAccountActionImpl
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage

val bankIntegrationModule = module {
    factory<CashBackIntegrationInteractor> { CashBackIntegrationInteractorImpl(get()) }
    factory<PresetIntegrationInteractor> { PresetIntegrationIntegrationInteractorImpl(get()) }
    factory<BankAccountRepository> { BankAccountRepositoryImpl(get(), get(), get()) }
    factory<BankAccountInterceptor> { BankAccountInterceptorImpl(get()) }

    factory<CashBackInfoDialogView> { CashBackInfoDialogViewImpl() }
    factory<BankPresetIconView> { BankPresetIconViewImpl() }
    factory<CashBackPresetIconView> { CashBackPresetIconViewImpl() }

    factory<LoadBankAccountsAction> { LoadBankAccountsActionImpl(get(), get()) }
    factory<SaveBankAccountAction> { SaveBankAccountActionImpl(get(), get(), get()) }

    feature { BankAccountFeature(get()) }
    page { BankAccountsPage(get(), get(), get(), get(), get(), get(), get()) }
    page { SelectBankAccountPage(get(), get(), get(), get()) }
    page { SaveBankAccountPage(get(), get(), get(), get()) }
}
