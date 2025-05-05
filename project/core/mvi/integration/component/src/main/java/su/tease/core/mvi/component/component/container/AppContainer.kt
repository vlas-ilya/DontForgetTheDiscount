package su.tease.core.mvi.component.component.container

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.mvi.component.resolver.NavigationTargetResolver
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.mvi.navigation.selector.appIdName
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.unit
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton

@Immutable
data class AppAction(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
)

private val emptyAppAction = AppAction(icon = 0, onClick = {})

private val emptyPageFloatingButton = DFPageFloatingButton(icon = 0, onClick = {})

@Immutable
data class AppConfig(
    val hasNavigationBar: Boolean = true,
    val hasBackButton: Boolean = true,
    val title: String = "",
    @StringRes val titleRes: Int? = null,
    val action: AppAction? = null,
    val floatingButtons: PersistentList<DFPageFloatingButton> = persistentListOf(),
    val additionalTitleContent: (@Composable () -> Unit)? = null,
)

private val actualAppConfigState = mutableStateOf(AppConfig())
fun commitAppConfig(config: AppConfig) {
    actualAppConfigState.value = config
}

@Immutable
class AppContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: NavigationTargetResolver,
) {
    @Composable
    @Suppress("LongMethod", "ModifierMissing")
    fun ComposeAppContainer(rootConfig: RootConfig, hasSystemNavigationBar: Boolean) {
        val (id, name) = store.select(appIdName()).collectAsState(null).value ?: return
        val app = remember(id, name) { navigationTargetResolver.resolve(id, name) }

        val rootConfigState = remember(app, rootConfig) { mutableStateOf(rootConfig) }
        val appConfigState = remember(app) { mutableStateOf(AppConfig()) }

        LaunchedEffect(app, rootConfigState, appConfigState) {
            app.setRootConfigState(rootConfigState)
            app.setAppConfigState(appConfigState)
        }

        val (
            hasNavigationBar,
            hasBackButton,
            title,
            titleRes,
            action,
            floatingButtons,
            additionalTitleContent,
        ) = actualAppConfigState.value

        Column(
            modifier = Modifier
                .background(Theme.colors.background0)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val featureContainer = remember {
                FeatureContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                )
            }

            if (!app.inPage) {
                app {
                    featureContainer.ComposeFeatureContainer(
                        rootConfig = rootConfigState.value,
                        appConfig = appConfigState.value,
                    )
                }
            } else {
                DFPage(
                    title = title.takeIf { it.isNotBlank() }
                        ?: titleRes?.takeIf { it != 0 }?.let { stringResource(it) }
                        ?: "",
                    additionalTitleContent = additionalTitleContent,
                    hasSystemNavigationBar = hasSystemNavigationBar,
                    modifier = Modifier
                        .padding(
                            top = Theme.sizes.padding2,
                            bottom = hasSystemNavigationBar.choose(
                                Theme.sizes.padding4,
                                Theme.sizes.size0,
                            ),
                            start = Theme.sizes.padding4,
                            end = Theme.sizes.padding4,
                        )
                        .background(Theme.colors.background1)
                        .weight(1F),
                    onBackPress = runIf(hasBackButton) {
                        {
                            store.dispatcher.dispatch(NavigationAction.Back).unit()
                        }
                    },
                    actionIcon = action?.takeIf { it != emptyAppAction }?.icon,
                    onActionPress = action?.takeIf { it != emptyAppAction }?.onClick,
                    floatingButtons = floatingButtons
                        .filter { it != emptyPageFloatingButton }
                        .toPersistentList()
                ) {
                    app {
                        featureContainer.ComposeFeatureContainer(
                            rootConfig = rootConfigState.value,
                            appConfig = appConfigState.value,
                        )
                    }
                }

                AnimatedVisibility(
                    visible = hasNavigationBar,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        app.ComposeNavigationBar()
                    }
                }
            }
        }
    }
}
