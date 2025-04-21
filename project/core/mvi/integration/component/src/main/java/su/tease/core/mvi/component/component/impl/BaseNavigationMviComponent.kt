package su.tease.core.mvi.component.component.impl

import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.navigation.action.NavigationAction

abstract class BaseNavigationMviComponent : BaseMviComponent() {

    protected fun Dispatcher.forward(
        page: NavigationTarget.Page,
        singleTop: Boolean = false,
    ) {
        dispatch(NavigationAction.ForwardToPage(page, singleTop))
    }

    protected fun Dispatcher.forward(
        feature: FeatureNavigation,
    ) {
        dispatch(NavigationAction.ForwardToFeature(feature))
    }

    protected fun Dispatcher.forward(
        app: AppNavigation,
    ) {
        dispatch(NavigationAction.ForwardToApp(app))
    }

    protected fun Dispatcher.replace(
        app: AppNavigation,
    ) {
        dispatch(NavigationAction.ReplaceApp(app))
    }

    protected fun Dispatcher.switch(
        feature: FeatureNavigation,
        clearStack: Boolean = false,
    ) {
        dispatch(NavigationAction.SwitchFeature(feature, clearStack))
    }

    protected fun Dispatcher.switch(
        app: AppNavigation,
        clearStack: Boolean = false,
    ) {
        dispatch(NavigationAction.SwitchApp(app, clearStack))
    }

    protected fun Dispatcher.back() {
        dispatch(NavigationAction.Back)
    }

    protected fun Dispatcher.backTo(
        page: NavigationTarget.Page,
    ) {
        dispatch(NavigationAction.BackToPage(page))
    }

    protected fun Dispatcher.backTo(
        feature: FeatureNavigation,
    ) {
        dispatch(NavigationAction.BackToFeature(feature))
    }

    protected fun Dispatcher.backTo(
        app: AppNavigation,
    ) {
        dispatch(NavigationAction.BackToApp(app))
    }

    protected fun Dispatcher.finish(
        feature: FeatureNavigation,
    ) {
        dispatch(NavigationAction.FinishFeature(feature))
    }

    protected fun Dispatcher.finish(
        app: AppNavigation,
    ) {
        dispatch(NavigationAction.FinishApp(app))
    }
}
