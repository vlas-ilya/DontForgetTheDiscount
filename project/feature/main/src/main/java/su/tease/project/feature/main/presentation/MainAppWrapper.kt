package su.tease.project.feature.main.presentation

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.mvi.component.component.container.AppConfig
import su.tease.core.mvi.component.component.container.emptyAppAction
import su.tease.core.mvi.component.component.container.emptyPageFloatingButton
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.unit
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.notification.impl.presentation.component.NotificationContainer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationState

@Composable
fun MainAppWrapper(
    store: Store<*>,
    app: BaseAppComponent,
    appConfig: State<AppConfig>,
    hasSystemNavigationBar: Boolean,
    composeFeatureContainer: @Composable () -> Unit,
    composeNavigationBar: @Composable () -> Unit,
) {
    val (
        hasNavigationBar,
        hasBackButton,
        title,
        titleRes,
        action,
        floatingButtons,
        additionalTitleContent,
    ) = appConfig.value

    val scope = rememberCoroutineScope()
    val notifications = store.select(scope, NotificationState::notifications).collectAsState()

    Box(
        Modifier
            .background(Theme.colors.background0)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            if (!app.inPage) {
                app {
                    composeFeatureContainer()
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
                        composeFeatureContainer()
                    }
                }
                AnimatedVisibility(
                    visible = hasNavigationBar,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        composeNavigationBar()
                    }
                }
            }
        }

        NotificationContainer(
            notificationsState = notifications,
            modifier = Modifier
                .padding(top = Theme.sizes.padding48)
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            onCloseNotification = {
                store.dispatcher.dispatch(NotificationAction.CloseNotification(it.id))
            }
        )
    }
}