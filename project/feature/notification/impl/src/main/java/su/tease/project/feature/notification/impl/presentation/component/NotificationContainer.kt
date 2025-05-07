package su.tease.project.feature.notification.impl.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.inFirstN
import su.tease.project.core.utils.ext.map
import su.tease.project.feature.notification.api.Notification

@Composable
fun NotificationContainer(
    notificationsState: State<PersistentList<Notification>>,
    modifier: Modifier = Modifier,
    onClickNotification: (Notification) -> Unit = {},
    onCloseNotification: (Notification) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val notifications = notificationsState.map { it.reversed().toPersistentList() }.value

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.sizes.padding4),
        verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding8),
    ) {
        items(
            count = notifications.size,
            key = { notifications[it].id },
        ) {
            val notification = notifications[it]
            NotificationItem(
                notification = notification,
                onClick = { onClickNotification(notification) },
                onDismiss = { onCloseNotification(notification) },
                shouldBeDismissed = !notifications.inFirstN(notification, NOTIFICATION_COUNT)
            )
        }
    }
}

private const val NOTIFICATION_COUNT = 3