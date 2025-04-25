package su.tease.core.mvi.component.component.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.Component
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.FeatureContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.pageIdName

class PageContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val root: RootContainerConfiguration,
    private val app: AppContainerConfiguration,
    private val feature: FeatureContainerConfiguration,
) : Component() {

    @Composable
    override operator fun invoke() {
        val (id, name) = store.select(pageIdName()).collectAsState(null).value ?: return

        val pageComponent = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        LaunchedEffect(id, name) {
            pageComponent.run {
                root.configure()
                app.configure()
                feature.configure()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            pageComponent()
        }
    }
}
