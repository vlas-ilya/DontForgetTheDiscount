package su.tease.project.core.mvi.navigation.selector

import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.project.core.mvi.navigation.state.NavigationState

fun page(): NavigationState.() -> PageNavigation = run { { root.page } }

fun feature(): NavigationState.() -> FeatureNavigation = run { { root.feature } }

fun app(): NavigationState.() -> AppNavigation = run { { root.app } }
