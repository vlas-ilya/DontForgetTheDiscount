package su.tease.core.component.component.impl

import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

abstract class BaseNavigationMviComponent<S : State, Target: NavigationTarget>(
    store: Store<S>,
    val target: Target,
) : BaseMviComponent<S>(store)
