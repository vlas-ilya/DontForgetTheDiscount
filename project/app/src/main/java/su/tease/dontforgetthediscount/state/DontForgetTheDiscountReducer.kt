package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.bank.presentation.list.reducer.BankAccountsReducer
import su.tease.project.feature.bank.presentation.save.reducer.SaveBankAccountReducer
import su.tease.project.feature.bank.presentation.select.reducer.SelectBankAccountReducer
import su.tease.project.feature.cashback.presentation.save.reducer.SaveCashBackReducer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationReducer
import su.tease.project.feature.preset.presentation.bank.save.reducer.SaveBankPresetReducer
import su.tease.project.feature.preset.presentation.bank.select.reducer.SelectBankPresetReducer
import su.tease.project.feature.preset.presentation.cashback.save.reducer.SaveCashBackPresetReducer
import su.tease.project.feature.preset.presentation.cashback.select.reducer.SelectCashBackPresetReducer
import su.tease.project.feature.shop.presentation.list.reducer.ShopsReducer

val dontForgetTheDiscountReducer = combine(

    NavigationReducer(),
    NotificationReducer(),

    BankAccountsReducer(),
    SelectBankAccountReducer(),
    SaveBankAccountReducer(),

    SaveCashBackReducer(),

    SelectBankPresetReducer(),
    SaveBankPresetReducer(),
    SelectCashBackPresetReducer(),
    SaveCashBackPresetReducer(),

    ShopsReducer(),
)
