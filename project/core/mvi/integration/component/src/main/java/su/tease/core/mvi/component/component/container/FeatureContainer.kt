package su.tease.core.mvi.component.component.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.FeatureContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.featureIdName

@Immutable
class FeatureContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val root: RootContainerConfiguration,
    private val app: AppContainerConfiguration,
): FeatureContainerConfiguration {

    @Composable
    fun ComposeFeatureContainer() {
        val (id, name) = store.select(featureIdName()).collectAsState(null).value ?: return

        val featureComponent = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        LaunchedEffect(id, name) {
            featureComponent.run {
                root.configure()
                app.configure()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            val pageContainer = remember {
                PageContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = root,
                    app = app,
                    feature = this@FeatureContainer,
                )
            }
            featureComponent {
                pageContainer.ComposePageContainer()
            }
        }
    }
}
