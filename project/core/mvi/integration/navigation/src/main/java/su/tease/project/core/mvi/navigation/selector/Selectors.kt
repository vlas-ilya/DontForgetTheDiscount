package su.tease.project.core.mvi.navigation.selector

import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.core.mvi.navigation.RootNavigation
import su.tease.project.core.mvi.navigation.state.NavigationState

fun page(): NavigationState.() -> PageNavigation =
    run { { root.page } }

fun feature(): NavigationState.() -> FeatureNavigation =
    run { { root.feature } }

fun app(): NavigationState.() -> AppNavigation =
    run { { root.app } }

fun root(): NavigationState.() -> RootNavigation =
    run { { root } }

fun pageIdName(): NavigationState.() -> Pair<String, NavigationTarget.Page> =
    run { { root.page.id to root.page.name } }

fun featureIdName(): NavigationState.() -> Pair<String, NavigationTarget.Feature> =
    run { { root.feature.id to root.feature.name } }

fun appIdName(): NavigationState.() -> Pair<String, NavigationTarget.App> =
    run { { root.app.id to root.app.name } }
