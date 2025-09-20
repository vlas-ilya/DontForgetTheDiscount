package su.tease.project.feature.shop.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.shop.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.shop.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.shop.data.repository.ShopRepositoryImpl
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.domain.interactor.impl.ShopInterceptorImpl
import su.tease.project.feature.shop.domain.repository.ShopRepository
import su.tease.project.feature.shop.integration.dependencies.data.CashBackIntegrationInteractorImpl
import su.tease.project.feature.shop.integration.dependencies.data.PresetIntegrationIntegrationInteractorImpl
import su.tease.project.feature.shop.integration.dependencies.presentation.view.ShopPresetIconViewImpl
import su.tease.project.feature.shop.integration.dependencies.presentation.view.CashBackInfoDialogViewImpl
import su.tease.project.feature.shop.integration.dependencies.presentation.view.CashBackPresetIconViewImpl
import su.tease.project.feature.shop.presentation.ShopFeature
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.shop.presentation.list.ShopsPage
import su.tease.project.feature.shop.presentation.list.action.LoadShopsAction
import su.tease.project.feature.shop.presentation.list.action.impl.LoadShopsActionImpl
import su.tease.project.feature.shop.presentation.save.SaveShopPage
import su.tease.project.feature.shop.presentation.save.action.SaveShopAction
import su.tease.project.feature.shop.presentation.save.action.impl.SaveShopActionImpl
import su.tease.project.feature.shop.presentation.select.SelectShopPage

val shopIntegrationModule = module {
    factory<CashBackIntegrationInteractor> { CashBackIntegrationInteractorImpl(get()) }
    factory<PresetIntegrationInteractor> { PresetIntegrationIntegrationInteractorImpl(get()) }
    factory<ShopRepository> { ShopRepositoryImpl(get(), get(), get()) }
    factory<ShopInterceptor> { ShopInterceptorImpl(get()) }

    factory<CashBackInfoDialogView> { CashBackInfoDialogViewImpl() }
    factory<ShopPresetIconView> { ShopPresetIconViewImpl() }
    factory<CashBackPresetIconView> { CashBackPresetIconViewImpl() }

    factory<LoadShopsAction> { LoadShopsActionImpl(get(), get()) }
    factory<SaveShopAction> { SaveShopActionImpl(get(), get(), get()) }

    feature { ShopFeature(get()) }
    page { ShopsPage(get(), get(), get(), get(), get(), get(), get()) }
    page { SelectShopPage(get(), get(), get(), get()) }
    page { SaveShopPage(get(), get(), get(), get()) }
}
