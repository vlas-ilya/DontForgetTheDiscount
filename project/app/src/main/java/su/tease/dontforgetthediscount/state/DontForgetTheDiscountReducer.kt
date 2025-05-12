package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.cacheback.presentation.list.reducer.ListCacheBackReducer
import su.tease.project.feature.cacheback.presentation.preset.bank.save.reducer.SaveBankPresetReducer
import su.tease.project.feature.cacheback.presentation.preset.bank.select.reducer.SelectBankPresetReducer
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.reducer.SaveCacheBackPresetReducer
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.SelectCacheBackPresetReducer
import su.tease.project.feature.cacheback.presentation.save.bank.save.reducer.SaveBankAccountReducer
import su.tease.project.feature.cacheback.presentation.save.bank.select.reducer.SelectBankAccountReducer
import su.tease.project.feature.cacheback.presentation.save.cacheback.reducer.SaveCacheBackReducer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationReducer

val dontForgetTheDiscountReducer = combine(
    NavigationReducer(),
    NotificationReducer(),

    ListCacheBackReducer(),
    SaveCacheBackReducer(),

    SelectBankPresetReducer(),
    SaveBankPresetReducer(),

    SelectBankAccountReducer(),
    SaveBankAccountReducer(),

    SelectCacheBackPresetReducer(),
    SaveCacheBackPresetReducer(),
)
