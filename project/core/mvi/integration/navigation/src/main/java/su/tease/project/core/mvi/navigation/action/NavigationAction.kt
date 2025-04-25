package su.tease.project.core.mvi.navigation.action

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction

@Parcelize
sealed class NavigationAction : PlainAction {

    data class ForwardToPage(
        val page: NavigationTarget.Page,
        val singleTop: Boolean = false,
    ) : NavigationAction()

    data class ForwardToFeature(
        val feature: FeatureNavigation,
    ) : NavigationAction()

    data class ForwardToApp(
        val app: AppNavigation,
    ) : NavigationAction()

    data class ReplaceApp(
        val app: AppNavigation,
    ) : NavigationAction()

    data class SwitchFeature(
        val feature: FeatureNavigation,
        val clearStack: Boolean = false,
    ) : NavigationAction()

    data class SwitchApp(
        val app: AppNavigation,
        val clearStack: Boolean = false,
    ) : NavigationAction()

    data object Back : NavigationAction()

    data class BackToPage(
        val page: NavigationTarget.Page,
    ) : NavigationAction()

    data class BackToFeature(
        val feature: FeatureNavigation,
    ) : NavigationAction()

    data class BackToApp(
        val app: AppNavigation,
    ) : NavigationAction()

    data class FinishFeature(
        val feature: FeatureNavigation,
    ) : NavigationAction()

    data class FinishApp(
        val app: AppNavigation,
    ) : NavigationAction()

    @Parcelize
    data object Start : PlainAction
}
