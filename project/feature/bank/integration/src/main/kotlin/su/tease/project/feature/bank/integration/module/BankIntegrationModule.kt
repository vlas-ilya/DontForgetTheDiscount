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
import su.tease.project.feature.bank.integration.presentation.save.action.SaveBankAccountSelectBankPresetActionImpl
import su.tease.project.feature.bank.presentation.BankAccountFeature
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.bank.presentation.info.list.BankAccountsInfoPage
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoAction
import su.tease.project.feature.bank.presentation.info.list.action.impl.LoadBankAccountsInfoActionImpl
import su.tease.project.feature.bank.presentation.info.save.SaveBankAccountInfoFeature
import su.tease.project.feature.bank.presentation.list.BankAccountsPage
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsAction
import su.tease.project.feature.bank.presentation.list.action.impl.LoadBankAccountsActionImpl
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountAction
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountSelectBankPresetAction
import su.tease.project.feature.bank.presentation.save.action.impl.SaveBankAccountActionImpl
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage
import su.tease.project.feature.bank.presentation.select.action.CreateBankAccountAction
import su.tease.project.feature.bank.presentation.select.action.impl.CreateBankAccountActionImpl

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
    factory<LoadBankAccountsInfoAction> { LoadBankAccountsInfoActionImpl(get()) }
    factory<SaveBankAccountSelectBankPresetAction> { SaveBankAccountSelectBankPresetActionImpl() }
    factory<CreateBankAccountAction> { CreateBankAccountActionImpl() }

    feature { BankAccountFeature(get()) }
    feature { SaveBankAccountInfoFeature(get()) }
    page { BankAccountsPage(get(), get(), get(), get(), get(), get(), get()) }
    page { BankAccountsInfoPage(get(), get(), get(), get()) }
    page { SelectBankAccountPage(get(), get(), get(), get(), get()) }
    page { SaveBankAccountPage(get(), get(), get(), get(), get()) }
}
