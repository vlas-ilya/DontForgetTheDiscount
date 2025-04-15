package su.tease.core.component.component.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.core.component.component.Component
import su.tease.core.component.resolver.NavigationTargetResolver
import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.selector.select
import su.tease.project.core.mvi_navigation.selector.app
import su.tease.project.core.mvi_navigation.selector.feature

class AppContainer<S : State>(
    private val store: Store<S>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val root: RootContainerConfiguration,
) : Component, AppContainerConfiguration {

    private val _hasNavigationBar = mutableStateOf(true)

    @Composable
    override fun Compose() {
        val appTarget = store.select(app()).collectAsState(null).value ?: return
        val featureTarget = store.select(feature()).collectAsState(null).value ?: return

        val appComponent = navigationTargetResolver.resolve(appTarget.name)

        LaunchedEffect(appTarget) {
            appComponent.run {
                root.configure()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            appComponent.Compose {
                FeatureContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = root,
                    app = this@AppContainer,
                ).Compose()
            }
            if (_hasNavigationBar.value) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                ) {
                    appComponent.ComposeNavigationBar(featureTarget.name)
                }
            }
        }
    }

    override var hasNavigationBar: Boolean
        get() = _hasNavigationBar.value
        set(value) {
            _hasNavigationBar.value = value
        }
}
