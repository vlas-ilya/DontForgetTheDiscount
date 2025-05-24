package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.cashback.presentation.list.reducer.ListCashBackReducer
import su.tease.project.feature.cashback.presentation.save.bank.save.reducer.SaveBankAccountReducer
import su.tease.project.feature.cashback.presentation.save.bank.select.reducer.SelectBankAccountReducer
import su.tease.project.feature.cashback.presentation.save.cashback.reducer.SaveCashBackReducer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationReducer
import su.tease.project.feature.preset.impl.presentation.bank.save.reducer.SaveBankPresetReducer
import su.tease.project.feature.preset.impl.presentation.bank.select.reducer.SelectBankPresetReducer
import su.tease.project.feature.preset.impl.presentation.cashback.save.reducer.SaveCashBackPresetReducer
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.SelectCashBackPresetReducer

val dontForgetTheDiscountReducer = combine(
    NavigationReducer(),
    NotificationReducer(),

    ListCashBackReducer(),
    SaveCashBackReducer(),

    SelectBankPresetReducer(),
    SaveBankPresetReducer(),

    SelectBankAccountReducer(),
    SaveBankAccountReducer(),

    SelectCashBackPresetReducer(),
    SaveCashBackPresetReducer(),
)
