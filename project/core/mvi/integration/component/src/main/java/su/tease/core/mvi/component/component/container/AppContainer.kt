package su.tease.core.mvi.component.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.appIdName

@Immutable
class AppContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val root: RootContainerConfiguration,
) : AppContainerConfiguration {

    private val _hasNavigationBar = mutableStateOf(true)

    @Composable
    fun ComposeAppContainer() {
        val (id, name) = store.select(appIdName()).collectAsState(null).value ?: return

        val appComponent = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        LaunchedEffect(id, name) {
            appComponent.run {
                root.configure()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(modifier = Modifier.weight(1F)) {
                val featureContainer = remember {
                    FeatureContainer(
                        store = store,
                        navigationTargetResolver = navigationTargetResolver,
                        root = root,
                        app = this@AppContainer,
                    )
                }
                appComponent {
                    featureContainer.ComposeFeatureContainer()
                }
            }
            if (_hasNavigationBar.value) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    appComponent.ComposeNavigationBar()
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
