package su.tease.dontforgetthediscount.component

import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.core.mvi.component.component.container.RootContainer
import su.tease.core.mvi.component.component.impl.BaseMviComponent
import su.tease.core.mvi.component.resolver.impl.AppNavigationTargetResolver
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.notification.impl.presentation.component.NotificationContainer
import su.tease.project.feature.notification.impl.presentation.reducer.NotificationState

class DontForgetTheDiscountComponent(
    store: Store<*>,
    private val navigationTargetResolver: AppNavigationTargetResolver,
    private val windowProvider: () -> Window,
) : BaseMviComponent(store) {

    @Composable
    @Suppress("ModifierMissing")
    fun ComposeDontForgetTheDiscountComponent() {
        val rootContainer = remember {
            RootContainer(
                store = store,
                navigationTargetResolver = navigationTargetResolver,
                windowProvider = windowProvider,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(Theme.colors.background0),
        ) {
            rootContainer.ComposeRootContainer()

            NotificationContainer(
                notificationsState = selectAsState(NotificationState::notifications),
                modifier = Modifier
                    .padding(top = Theme.sizes.padding46)
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                onCloseNotification = { dispatch(NotificationAction.CloseNotification(it.id)) }
            )
        }
    }
}
