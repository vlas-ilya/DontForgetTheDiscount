package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import su.tease.core.mvi.component.component.container.AppConfig
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.container.RootConfig
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.ComposableContent

abstract class BaseFeatureComponent(store: Store<*>) : BaseNavigationMviComponent(store) {

    private lateinit var rootConfigState: MutableState<RootConfig>
    private lateinit var appConfigState: MutableState<AppConfig>
    private lateinit var featureConfigState: MutableState<FeatureConfig>

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
    open operator fun invoke(child: ComposableContent) = child()

    internal fun setRootConfigState(rootConfigState: MutableState<RootConfig>) {
        this.rootConfigState = rootConfigState
    }

    internal fun setAppConfigState(appConfigState: MutableState<AppConfig>) {
        this.appConfigState = appConfigState
    }

    internal fun setFeatureConfigState(featureConfigState: MutableState<FeatureConfig>) {
        this.featureConfigState = featureConfigState
    }

    fun rootConfig(builder: RootConfig.() -> RootConfig) {
        rootConfigState.value = builder(rootConfigState.value)
    }

    fun appConfig(builder: AppConfig.() -> AppConfig) {
        appConfigState.value = builder(appConfigState.value)
    }

    fun featureConfig(builder: FeatureConfig.() -> FeatureConfig) {
        featureConfigState.value = builder(featureConfigState.value)
    }
}
