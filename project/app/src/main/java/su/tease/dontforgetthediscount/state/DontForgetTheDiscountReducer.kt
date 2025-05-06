package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer
import su.tease.project.feature.cacheback.presentation.reducer.AddBankReducer
import su.tease.project.feature.cacheback.presentation.reducer.AddCacheBackReducer
import su.tease.project.feature.cacheback.presentation.reducer.ListCacheBackReducer

val dontForgetTheDiscountReducer = combine(
    NavigationReducer(),
    ListCacheBackReducer(),
    AddCacheBackReducer(),
    AddBankReducer(),
)
