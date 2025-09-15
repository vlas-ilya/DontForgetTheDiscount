package su.tease.project.feature.bank.integration.module.integration

import org.koin.dsl.module
import su.tease.project.feature.bank.integration.dependencies.data.CashBackOwnerIntegrationInteractorImpl
import su.tease.project.feature.bank.integration.dependencies.presentation.view.CashBackOwnerPreviewViewImpl
import su.tease.project.feature.bank.integration.dependencies.presentation.view.CashBackOwnerTextImpl
import su.tease.project.feature.cashback.data.dependencies.CashBackOwnerIntegrationInteractor
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerText

val bankCashBackIntegrationModule = module {
    factory<CashBackOwnerIntegrationInteractor> { CashBackOwnerIntegrationInteractorImpl(get()) }
    factory<CashBackOwnerPreviewView> { CashBackOwnerPreviewViewImpl() }
    factory<CashBackOwnerText> { CashBackOwnerTextImpl() }
}
