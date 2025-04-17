package su.tease.core.mvi.component.component.provider

import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

data class StoreTargetProvider<T : NavigationTarget>(
    val target: T,
    val store: Store<*>
)
