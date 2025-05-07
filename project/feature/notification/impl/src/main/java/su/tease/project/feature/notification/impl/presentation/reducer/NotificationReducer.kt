package su.tease.project.feature.notification.impl.presentation.reducer

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction

@Parcelize
data class NotificationState(
    val notifications: PersistentList<Notification> = persistentListOf()
) : State

class NotificationReducer : Reducer<NotificationState> {
    override val initState = NotificationState()

    override fun NotificationState.onAction(action: PlainAction) = when (action) {
        is NotificationAction -> onNotificationAction(action)
        else -> this
    }

    private fun NotificationState.onNotificationAction(action: NotificationAction) = when (action) {

        is NotificationAction.ShowNotification -> copy(
            notifications = (notifications + action.notification).toPersistentList()
        )

        is NotificationAction.CloseNotification -> copy(
            notifications = notifications.filter { it.id != action.id }.toPersistentList()
        )
    }
}