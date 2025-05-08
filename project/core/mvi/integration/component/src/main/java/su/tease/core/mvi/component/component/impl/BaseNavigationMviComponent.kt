package su.tease.core.mvi.component.component.impl

import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.design.component.controls.page.DFPageContext

@Suppress("TooManyFunctions")
abstract class BaseNavigationMviComponent(
    store: Store<*>
) : BaseMviComponent(store), DFPageContext {

    override fun onBackPress() {
        back()
    }

    protected fun forward(
        page: NavigationTarget.Page,
        singleTop: Boolean = false,
    ) = dispatch(NavigationAction.ForwardToPage(page, singleTop))

    protected fun forward(
        feature: FeatureNavigation,
    ) = dispatch(NavigationAction.ForwardToFeature(feature))

    protected fun forward(
        app: AppNavigation,
    ) = dispatch(NavigationAction.ForwardToApp(app))

    protected fun replace(
        app: AppNavigation,
    ) = dispatch(NavigationAction.ReplaceApp(app))

    protected fun switch(
        feature: FeatureNavigation,
        clearStack: Boolean = false,
    ) = dispatch(NavigationAction.SwitchFeature(feature, clearStack))

    protected fun switch(
        app: AppNavigation,
        clearStack: Boolean = false,
    ) = dispatch(NavigationAction.SwitchApp(app, clearStack))

    protected fun back() = dispatch(NavigationAction.Back)

    protected fun backTo(
        page: NavigationTarget.Page,
    ) = dispatch(NavigationAction.BackToPage(page))

    protected fun backTo(
        feature: FeatureNavigation,
    ) = dispatch(NavigationAction.BackToFeature(feature))

    protected fun backTo(
        app: AppNavigation,
    ) = dispatch(NavigationAction.BackToApp(app))

    protected fun finish(
        feature: FeatureNavigation,
    ) = dispatch(NavigationAction.FinishFeature(feature))

    protected fun finish(
        app: AppNavigation,
    ) = dispatch(NavigationAction.FinishApp(app))
}
