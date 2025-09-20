package su.tease.dontforgetthediscount.state

import su.tease.feature.splash.integration.dependencies.presentation.navigation.MainPageFromSplashInterceptor
import su.tease.project.feature.bank.integration.dependencies.presentation.navigation.SaveCashBackPageFromBankAccountsPageInterceptor
import su.tease.project.feature.bank.integration.dependencies.presentation.navigation.SelectBankPresetPageFromSaveBankPageInterceptor
import su.tease.project.feature.cashback.integration.dependencies.presentation.navigation.SelectCashBackOwnerPageFromSaveCashBackInterceptor
import su.tease.project.feature.cashback.integration.dependencies.presentation.navigation.SelectCashBackPresetPageFromCashBackInterceptor
import su.tease.project.feature.shop.integration.dependencies.presentation.navigation.SaveCashBackPageFromShopsPageInterceptor
import su.tease.project.feature.shop.integration.dependencies.presentation.navigation.SelectShopPresetPageFromSaveShopPageInterceptor

val dontForgetTheDiscountInterceptors = listOf(
    SaveCashBackPageFromBankAccountsPageInterceptor(),
    SelectBankPresetPageFromSaveBankPageInterceptor(),
    SelectCashBackOwnerPageFromSaveCashBackInterceptor(),
    SelectCashBackPresetPageFromCashBackInterceptor(),
    SaveCashBackPageFromShopsPageInterceptor(),
    SelectShopPresetPageFromSaveShopPageInterceptor(),
    MainPageFromSplashInterceptor(),
)
