package su.tease.core.mvi.component.component.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.Component
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.FeatureContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.selector.select
import su.tease.project.core.mvi.navigation.selector.feature

class FeatureContainer<S : State>(
    private val store: Store<S>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val root: RootContainerConfiguration,
    private val app: AppContainerConfiguration,
) : Component, FeatureContainerConfiguration {

    @Composable
    override fun Compose() {
        val featureTarget = store.select(feature()).collectAsState(null).value ?: return

        val featureComponent = navigationTargetResolver.resolve(featureTarget.name)

        LaunchedEffect(featureTarget) {
            featureComponent.run {
                root.configure()
                app.configure()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            featureComponent.Compose {
                PageContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                    root = root,
                    app = app,
                    feature = this@FeatureContainer,
                ).Compose()
            }
        }
    }
}
