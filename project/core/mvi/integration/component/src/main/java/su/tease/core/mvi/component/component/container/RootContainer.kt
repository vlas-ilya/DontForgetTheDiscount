@file:Suppress("ForbiddenComment")

package su.tease.core.mvi.component.component.container

import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.hideSystemUI
import su.tease.project.core.utils.ext.isNavigationBarVisible
import su.tease.project.core.utils.ext.showSystemUI

@Immutable
data class RootConfig(
    val isFullscreen: Boolean = false
)

private val actualRootConfigState = mutableStateOf(RootConfig())
fun commitConfigRoot(config: RootConfig) {
    actualRootConfigState.value = config
}

class RootContainer(
    private val store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
    private val windowProvider: () -> Window,
) {
    @Composable
    @Suppress("ModifierMissing")
    fun ComposeRootContainer() {
        val rootConfigState = remember { mutableStateOf(RootConfig()) }

        val isFullscreen = actualRootConfigState.value.isFullscreen
        LaunchedEffect(isFullscreen) {
            if (isFullscreen) {
                windowProvider().hideSystemUI()
            } else {
                windowProvider().showSystemUI()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(Theme.colors.background1),
        ) {
            val appContainer = remember {
                AppContainer(
                    store = store,
                    navigationTargetResolver = navigationTargetResolver,
                )
            }

            windowProvider().isNavigationBarVisible()

            val hasSystemNavigationBar =
                !actualRootConfigState.value.isFullscreen && windowProvider().isNavigationBarVisible()

            appContainer.ComposeAppContainer(
                rootConfig = rootConfigState.value,
                hasSystemNavigationBar = hasSystemNavigationBar,
            )
        }
    }
}
