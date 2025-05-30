package su.tease.project.core.mvi.navigation.reducer

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.mvi.navigation.state.NavigationState

class NavigationReducer : Reducer<NavigationState> {
    override val initState = NavigationState()

    override fun NavigationState.onAction(action: PlainAction) = when (action) {
        is NavigationAction -> onNavigationAction(action)
        is NavigationAction.Start -> copy(finished = false)
        else -> this
    }

    private fun NavigationState.onNavigationAction(action: NavigationAction) = when (action) {
        is NavigationAction.Back -> root.back()
        is NavigationAction.BackToApp -> root.backToApp(action.app)
        is NavigationAction.BackToFeature -> root.backToFeature(action.feature)
        is NavigationAction.BackToPage -> root.backToPage(action.page)
        is NavigationAction.FinishApp -> root.finishApp(action.app)
        is NavigationAction.FinishFeature -> root.finishFeature(action.feature)
        is NavigationAction.ForwardToApp -> root.forward(action.app)
        is NavigationAction.ForwardToFeature -> root.forward(action.feature)
        is NavigationAction.ForwardToPage -> root.forward(action.page)
        is NavigationAction.SwitchApp -> root.switchTo(action.app, action.clearStack)
        is NavigationAction.SwitchFeature -> root.switchTo(action.feature, action.clearStack)
        is NavigationAction.ReplaceApp -> root.replace(action.app)
    }.let { if (it == null) copy(finished = true) else copy(root = it) }
}
