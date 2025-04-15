package su.tease.project.core.mvi_navigation.action

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.project.core.mvi.api.action.PlainAction

sealed interface NavigationAction : PlainAction {

    @Parcelize
    data class ForwardToPage(
        val page: NavigationTarget.Page,
        val singleTop: Boolean = false,
    ) : NavigationAction

    @Parcelize
    data class ForwardToFeature(
        val feature: FeatureNavigation,
    ) : NavigationAction

    @Parcelize
    data class ForwardToApp(
        val app: AppNavigation,
    ) : NavigationAction

    @Parcelize
    data class ReplaceApp(
        val app: AppNavigation,
    ) : NavigationAction

    @Parcelize
    data class SwitchFeature(
        val feature: FeatureNavigation,
        val clearStack: Boolean = false,
    ) : NavigationAction

    @Parcelize
    data class SwitchApp(
        val app: AppNavigation,
        val clearStack: Boolean = false,
    ) : NavigationAction

    @Parcelize
    data object Back : NavigationAction

    @Parcelize
    data class BackToPage(
        val page: NavigationTarget.Page
    ) : NavigationAction

    @Parcelize
    data class BackToFeature(
        val feature: FeatureNavigation
    ) : NavigationAction

    @Parcelize
    data class BackToApp(
        val app: AppNavigation
    ) : NavigationAction

    @Parcelize
    data class FinishFeature(
        val feature: FeatureNavigation
    ) : NavigationAction

    @Parcelize
    data class FinishApp(
        val app: AppNavigation
    ) : NavigationAction
}
