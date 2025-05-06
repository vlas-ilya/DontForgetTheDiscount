@file:Suppress("ForbiddenComment")

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
import su.tease.project.core.mvi.navigation.selector.featureIdName

@Immutable
data class FeatureConfig(
    val tmp: String? = null, // TODO: ONLY FOT TEST NOW
)

private val actualFeatureConfigState = mutableStateOf(FeatureConfig())
fun commitFeatureConfig(config: FeatureConfig) {
    actualFeatureConfigState.value = config
}

@Immutable
class FeatureContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
) {
    @Composable
    @Suppress("ModifierMissing")
    fun ComposeFeatureContainer(
        rootConfig: RootConfig,
        appConfig: AppConfig,
    ) {
        val scope = rememberCoroutineScope()
        val (id, name) = remember { store.select(scope, featureIdName()) }.collectAsState().value
        val feature = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        val rootConfigState = remember(feature, rootConfig) { mutableStateOf(rootConfig) }
        val appConfigState = remember(feature, appConfig) { mutableStateOf(appConfig) }
        val featureConfigState = remember(feature) { mutableStateOf(FeatureConfig()) }

        LaunchedEffect(feature, rootConfigState, appConfigState, featureConfigState) {
            feature.setRootConfigState(rootConfigState)
            feature.setAppConfigState(appConfigState)
            feature.setFeatureConfigState(featureConfigState)
        }

        val (tmp) = actualFeatureConfigState.value
        LaunchedEffect(tmp) {
            println(tmp) // TODO: ONLY FOT TEST NOW
        }

        Box(modifier = Modifier.fillMaxSize()) {
            val pageContainer = remember {
                PageContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                )
            }
            feature {
                pageContainer.ComposePageContainer(
                    rootConfig = rootConfigState.value,
                    appConfig = appConfigState.value,
                    featureConfig = featureConfigState.value,
                )
            }
        }
    }
}
