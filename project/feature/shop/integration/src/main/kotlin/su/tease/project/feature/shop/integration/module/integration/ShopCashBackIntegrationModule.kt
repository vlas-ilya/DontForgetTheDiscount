package su.tease.project.feature.shop.integration.module.integration

import org.koin.dsl.module
import su.tease.project.feature.cashback.data.dependencies.CashBackOwnerIntegrationInteractor
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerText
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetAction
import su.tease.project.feature.shop.integration.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerActionImpl
import su.tease.project.feature.shop.integration.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetActionImpl
import su.tease.project.feature.shop.integration.dependencies.data.CashBackOwnerIntegrationInteractorImpl
import su.tease.project.feature.shop.integration.dependencies.presentation.view.CashBackOwnerPreviewViewImpl
import su.tease.project.feature.shop.integration.dependencies.presentation.view.CashBackOwnerTextImpl

val shopCashBackIntegrationModule = module {
    factory<CashBackOwnerIntegrationInteractor> { CashBackOwnerIntegrationInteractorImpl(get()) }
    factory<CashBackOwnerPreviewView> { CashBackOwnerPreviewViewImpl() }
    factory<CashBackOwnerText> { CashBackOwnerTextImpl() }
    factory<SaveCashBackSelectCashBackOwnerAction> { SaveCashBackSelectCashBackOwnerActionImpl() }
    factory<SaveCashBackSelectCashBackPresetAction> { SaveCashBackSelectCashBackPresetActionImpl() }
}
