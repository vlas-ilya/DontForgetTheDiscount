package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.navigation.reducer.NavigationReducer

val dontForgetTheDiscountReducer = combine(
    NavigationReducer(),
)
