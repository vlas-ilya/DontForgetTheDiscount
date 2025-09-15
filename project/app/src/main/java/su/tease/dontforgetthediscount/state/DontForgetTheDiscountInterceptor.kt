package su.tease.dontforgetthediscount.state

import su.tease.project.feature.bank.integration.dependencies.presentation.navigation.SaveCashBackPageFromBankAccountsPageInterceptor
import su.tease.project.feature.bank.integration.dependencies.presentation.navigation.SelectBankPresetPageFromSaveBankPageInterceptor
import su.tease.project.feature.cashback.integration.dependencies.presentation.navigation.SelectCashBackOwnerPageFromSaveCashBackInterceptor
import su.tease.project.feature.cashback.integration.dependencies.presentation.navigation.SelectCashBackPresetPageFromCashBackInterceptor

val dontForgetTheDiscountInterceptors = listOf(
    SaveCashBackPageFromBankAccountsPageInterceptor(),
    SelectBankPresetPageFromSaveBankPageInterceptor(),
    SelectCashBackOwnerPageFromSaveCashBackInterceptor(),
    SelectCashBackPresetPageFromCashBackInterceptor(),
)
