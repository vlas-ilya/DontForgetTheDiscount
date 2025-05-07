package su.tease.core.mvi.component.component.container

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.appIdName
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.function.Supplier
import su.tease.project.design.component.controls.page.DFPageFloatingButton

@Immutable
data class AppAction(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
)

val emptyAppAction = AppAction(icon = 0, onClick = {})

val emptyPageFloatingButton = DFPageFloatingButton(icon = 0, onClick = {})

@Immutable
data class AppConfig(
    val hasNavigationBar: Boolean = true,
    val hasBackButton: Boolean = true,
    val title: String = "",
    @StringRes val titleRes: Int? = null,
    val action: AppAction? = null,
    val floatingButtons: PersistentList<DFPageFloatingButton> = persistentListOf(),
    val additionalTitleContent: @Composable () -> Unit = {},
)

private val actualAppConfigState = mutableStateOf(AppConfig())
fun commitAppConfig(config: AppConfig) {
    actualAppConfigState.value = config
}

@Immutable
fun interface AppWrapper {

    @Composable
    fun ComposeAppWrapper(
        app: BaseAppComponent,
        appConfig: State<AppConfig>,
        hasSystemNavigationBar: Boolean,
        composeFeatureContainer: @Composable () -> Unit,
        composeNavigationBar: @Composable () -> Unit,
    )
}

@Immutable
class AppContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
    private val appWrapper: AppWrapper,
) {
    @Composable
    @Suppress("LongMethod", "ModifierMissing")
    fun ComposeAppContainer(
        rootConfig: State<Supplier<RootConfig>>,
        hasSystemNavigationBar: Boolean
    ) {
        val scope = rememberCoroutineScope()
        val (id, name) = remember { store.select(scope, appIdName()) }.collectAsState().value
        val app = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        val featureContainer = remember {
            FeatureContainer(
                store = store,
                navigationTargetResolver = navigationTargetResolver,
            )
        }

        appWrapper.ComposeAppWrapper(
            app = app,
            appConfig = actualAppConfigState,
            hasSystemNavigationBar = hasSystemNavigationBar,
            composeFeatureContainer = {
                featureContainer.ComposeFeatureContainer(
                    rootConfig = app.rootConfig.map { Supplier { it(rootConfig.value()) } },
                    appConfig = app.appConfig.map { Supplier { it(AppConfig()) } },
                )
            },
            composeNavigationBar = { app.ComposeNavigationBar() }
        )
    }
}
