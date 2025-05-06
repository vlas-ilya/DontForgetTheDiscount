package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import su.tease.core.mvi.component.component.container.AppConfig
import su.tease.core.mvi.component.component.container.RootConfig
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.function.Transformer

abstract class BaseAppComponent(
    store: Store<*>,
) : BaseNavigationMviComponent(store) {

    open val inPage: Boolean = true

    internal val rootConfig = mutableStateOf<Transformer<RootConfig>>(Transformer { it })
    internal val appConfig = mutableStateOf<Transformer<AppConfig>>(Transformer { it })

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

    @Composable
    fun RootConfig(vararg key: Any, builder: RootConfig.() -> RootConfig) {
        LaunchedEffect(*key, builder) {
            rootConfig.value = Transformer { builder(it) }
        }
    }

    @Composable
    fun AppConfig(vararg key: Any, builder: AppConfig.() -> AppConfig) {
        LaunchedEffect(*key, builder) {
            appConfig.value = Transformer { builder(it) }
        }
    }
}
