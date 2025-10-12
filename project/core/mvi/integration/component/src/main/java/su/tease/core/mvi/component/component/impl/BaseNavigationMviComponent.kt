package su.tease.core.mvi.component.component.impl

import kotlinx.coroutines.Job
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.design.component.controls.page.DFPageContext

abstract class BaseNavigationMviComponent(
    store: Store<*>
) : BaseMviComponent(store), DFPageContext, NavigationComponent by NavigationComponentImpl(store) {

    override fun onBackPress() = back()
}

@Suppress("TooManyFunctions")
interface NavigationComponent {
    fun NavigationTarget.Page.forward(singleTop: Boolean = false): Job
    fun FeatureNavigation.forward(): Job
    fun AppNavigation.forward(): Job
    fun AppNavigation.replace(): Job
    fun FeatureNavigation.switchTo(clearStack: Boolean = false): Job
    fun AppNavigation.switchTo(clearStack: Boolean = false): Job
    fun back(): Job
    fun NavigationTarget.Page.backTo(): Job
    fun FeatureNavigation.backTo(): Job
    fun AppNavigation.backTo(): Job
    fun FeatureNavigation.finish(): Job
    fun AppNavigation.finish(): Job
    fun BaseFeatureComponent<*>.finish(): Job
    fun BaseAppComponent<*>.finish(): Job
}

@Suppress("TooManyFunctions")
class NavigationComponentImpl(store: Store<*>) : NavigationComponent, Dispatcher by store.dispatcher {
    override fun NavigationTarget.Page.forward(singleTop: Boolean) = dispatch(NavigationAction.ForwardToPage(this, singleTop))
    override fun FeatureNavigation.forward() = dispatch(NavigationAction.ForwardToFeature(this))
    override fun AppNavigation.forward() = dispatch(NavigationAction.ForwardToApp(this))
    override fun AppNavigation.replace() = dispatch(NavigationAction.ReplaceApp(this))
    override fun FeatureNavigation.switchTo(clearStack: Boolean) = dispatch(NavigationAction.SwitchFeature(this, clearStack))
    override fun AppNavigation.switchTo(clearStack: Boolean) = dispatch(NavigationAction.SwitchApp(this, clearStack))
    override fun back() = dispatch(NavigationAction.Back)
    override fun NavigationTarget.Page.backTo() = dispatch(NavigationAction.BackToPage(this))
    override fun FeatureNavigation.backTo() = dispatch(NavigationAction.BackToFeature(this))
    override fun AppNavigation.backTo() = dispatch(NavigationAction.BackToApp(this))
    override fun FeatureNavigation.finish() = dispatch(NavigationAction.FinishFeature(this))
    override fun AppNavigation.finish() = dispatch(NavigationAction.FinishApp(this))
    override fun BaseFeatureComponent<*>.finish() = dispatch(NavigationAction.FinishFeature(this.featureNavigation))
    override fun BaseAppComponent<*>.finish() = dispatch(NavigationAction.FinishApp(this.appNavigation))
}
