package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import su.tease.core.mvi.component.component.container.AppConfig
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.container.RootConfig
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.function.Transformer

abstract class BasePageComponent<T : NavigationTarget.Page>(
    store: Store<*>
) : BaseNavigationMviComponent(store) {

    internal val rootConfig = mutableStateOf<Transformer<RootConfig>>(Transformer { it })
    internal val appConfig = mutableStateOf<Transformer<AppConfig>>(Transformer { it })
    internal val featureConfig = mutableStateOf<Transformer<FeatureConfig>>(Transformer { it })

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

    @Composable
    fun FeatureConfig(vararg key: Any, builder: FeatureConfig.() -> FeatureConfig) {
        LaunchedEffect(*key, builder) {
            featureConfig.value = Transformer { builder(it) }
        }
    }

    @Composable
    abstract operator fun invoke()
}
