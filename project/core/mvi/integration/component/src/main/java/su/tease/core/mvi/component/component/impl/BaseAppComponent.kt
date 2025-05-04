package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import su.tease.core.mvi.component.component.container.AppConfig
import su.tease.core.mvi.component.component.container.RootConfig
import su.tease.project.core.mvi.api.store.Store

abstract class BaseAppComponent(
    store: Store<*>,
) : BaseNavigationMviComponent(store) {

    open val inPage: Boolean = true

    private lateinit var rootConfigState: MutableState<RootConfig>
    private lateinit var appConfigState: MutableState<AppConfig>

    @Composable
    open fun ComposeNavigationBar() = Unit

    @Composable
    @Deprecated(
        message = "Use Compose(child)",
        replaceWith = ReplaceWith("Compose(child)"),
        level = DeprecationLevel.ERROR,
    )
    override operator fun invoke() {
        error("Not supported")
    }

    @Composable
    open operator fun invoke(child: @Composable () -> Unit) = child()

    internal fun setRootConfigState(rootConfigState: MutableState<RootConfig>) {
        this.rootConfigState = rootConfigState
    }

    internal fun setAppConfigState(appConfigState: MutableState<AppConfig>) {
        this.appConfigState = appConfigState
    }

    fun rootConfig(builder: RootConfig.() -> RootConfig) {
        rootConfigState.value = builder(rootConfigState.value)
    }

    fun appConfig(builder: AppConfig.() -> AppConfig) {
        appConfigState.value = builder(appConfigState.value)
    }
}
