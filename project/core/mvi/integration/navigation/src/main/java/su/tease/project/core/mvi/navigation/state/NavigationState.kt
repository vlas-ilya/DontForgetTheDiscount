package su.tease.project.core.mvi.navigation.state

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.core.mvi.navigation.RootNavigation
import su.tease.project.core.mvi.api.state.State

@Parcelize
data class NavigationState(
    val root: RootNavigation = baseRootNavigation,
    val top: PageNavigation = baseRootNavigation.page,
) : State
