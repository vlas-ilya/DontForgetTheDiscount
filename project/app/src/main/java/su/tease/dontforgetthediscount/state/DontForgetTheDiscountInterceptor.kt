package su.tease.dontforgetthediscount.state

import su.tease.feature.splash.integration.dependencies.presentation.navigation.MainPageFromSplashInterceptor
import su.tease.project.feature.bank.integration.dependencies.presentation.navigation.SaveCashBackPageFromBankAccountsPageInterceptor
import su.tease.project.feature.info.integration.module.dependencies.presentation.navigation.InfoListPageNavigationInterceptor
import su.tease.project.feature.shop.integration.dependencies.presentation.navigation.SaveCashBackPageFromShopsPageInterceptor

val dontForgetTheDiscountInterceptors = listOf(
    SaveCashBackPageFromBankAccountsPageInterceptor(),
    SaveCashBackPageFromShopsPageInterceptor(),
    InfoListPageNavigationInterceptor(),
    MainPageFromSplashInterceptor(),
)
