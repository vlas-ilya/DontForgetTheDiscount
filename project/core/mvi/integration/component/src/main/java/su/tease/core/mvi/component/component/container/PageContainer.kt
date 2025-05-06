package su.tease.core.mvi.component.component.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.pageIdName

@Immutable
class PageContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
) {
    @Composable
    @Suppress("ModifierMissing")
    fun ComposePageContainer(
        rootConfig: RootConfig,
        appConfig: AppConfig,
        featureConfig: FeatureConfig,
    ) {
        val scope = rememberCoroutineScope()
        val (id, name) = remember { store.select(scope, pageIdName()) }.collectAsState().value
        val page = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        val rootConfigState = remember(page, rootConfig) { mutableStateOf(rootConfig) }
        val appConfigState = remember(page, appConfig) { mutableStateOf(appConfig) }
        val featureConfigState = remember(page, featureConfig) { mutableStateOf(featureConfig) }

        LaunchedEffect(page, rootConfigState, appConfigState, featureConfigState) {
            page.setRootConfigState(rootConfigState)
            page.setAppConfigState(appConfigState)
            page.setFeatureConfigState(featureConfigState)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            page()

            LaunchedEffect(rootConfigState.value) {
                commitConfigRoot(rootConfigState.value)
            }

            LaunchedEffect(appConfigState.value) {
                commitAppConfig(appConfigState.value)
            }

            LaunchedEffect(featureConfigState.value) {
                commitFeatureConfig(featureConfigState.value)
            }
        }
    }
}
