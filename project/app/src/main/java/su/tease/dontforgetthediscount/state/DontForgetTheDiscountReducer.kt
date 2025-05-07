package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.cacheback.presentation.reducer.AddBankReducer
import su.tease.project.feature.cacheback.presentation.reducer.ListCacheBackReducer
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackReducer
import su.tease.project.feature.cacheback.presentation.reducer.SelectBankReducer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationReducer

val dontForgetTheDiscountReducer = combine(
    NavigationReducer(),
    ListCacheBackReducer(),
    SaveCacheBackReducer(),
    SelectBankReducer(),
    AddBankReducer(),
    NotificationReducer(),
)
