package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.bank.presentation.info.list.reducer.BankAccountsInfoPageReducer
import su.tease.project.feature.bank.presentation.list.reducer.BankAccountsPageReducer
import su.tease.project.feature.bank.presentation.save.reducer.SaveBankAccountPageReducer
import su.tease.project.feature.cashback.presentation.save.reducer.SaveCashBackReducer
import su.tease.project.feature.icon.presentation.reducer.SelectIconReducer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationReducer
import su.tease.project.feature.preset.presentation.bank.save.reducer.SaveBankPresetReducer
import su.tease.project.feature.preset.presentation.cashback.info.list.reducer.ListInfoCashbackPresetReducer
import su.tease.project.feature.preset.presentation.cashback.save.reducer.SaveCashBackPresetReducer
import su.tease.project.feature.preset.presentation.cashback.select.reducer.SelectCashBackPresetReducer
import su.tease.project.feature.preset.presentation.shop.save.reducer.SaveShopPresetReducer
import su.tease.project.feature.shop.presentation.info.list.reducer.ShopsInfoPageReducer
import su.tease.project.feature.shop.presentation.list.reducer.ShopsReducer
import su.tease.project.feature.shop.presentation.save.reducer.SaveShopPageReducer

val dontForgetTheDiscountReducer = combine(

    NavigationReducer(),
    NotificationReducer(),

    BankAccountsPageReducer(),
    BankAccountsInfoPageReducer(),
    SaveBankAccountPageReducer(),

    ShopsReducer(),
    ShopsInfoPageReducer(),
    SaveShopPageReducer(),

    SaveCashBackReducer(),

    SaveBankPresetReducer(),

    SaveShopPresetReducer(),

    ListInfoCashbackPresetReducer(),
    SelectCashBackPresetReducer(),
    SaveCashBackPresetReducer(),

    SelectIconReducer(),
)
