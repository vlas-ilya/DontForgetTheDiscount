package su.tease.project.core.mvi_navigation.reducer

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi_navigation.action.NavigationAction
import su.tease.project.core.mvi_navigation.state.NavigationState
import su.tease.project.core.mvi_navigation.state.finishRootNavigationTarget

class NavigationReducer : Reducer<NavigationState> {

    override val initState = NavigationState()

    override fun NavigationState.onAction(
        action: PlainAction
    ) = when (action) {
        is NavigationAction -> onNavigationAction(action)
        else -> this
    }

    private fun NavigationState.onNavigationAction(
        action: NavigationAction
    ) = when (action) {
        is NavigationAction.Back -> root.back()
        is NavigationAction.BackToApp -> root.backTo(action.app)
        is NavigationAction.BackToFeature -> root.backTo(action.feature)
        is NavigationAction.BackToPage -> root.backTo(action.page)
        is NavigationAction.FinishApp -> root.finish(action.app)
        is NavigationAction.FinishFeature -> root.finish(action.feature)
        is NavigationAction.ForwardToApp -> root.forward(action.app)
        is NavigationAction.ForwardToFeature -> root.forward(action.feature)
        is NavigationAction.ForwardToPage -> root.forward(action.page)
        is NavigationAction.SwitchApp -> root.switchTo(action.app, action.clearStack)
        is NavigationAction.SwitchFeature -> root.switchTo(action.feature, action.clearStack)
        is NavigationAction.ReplaceApp -> root.replace(action.app)
    }
        ?.let { copy(root = it) }
        ?: copy(root = finishRootNavigationTarget)
}
