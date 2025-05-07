package su.tease.project.feature.notification.api

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction

@Parcelize
sealed class NotificationAction : PlainAction {
    data class ShowNotification(val notification: Notification) : NotificationAction()
    data class CloseNotification(val id: String) : NotificationAction()
}

@Parcelize
data class Notification(
    val id: String,
    val type: Type,
    val title: String? = null,
    val text: String? = null,
    val closable: Boolean = true,
) : Parcelable {

    enum class Type(
        val background: @Composable () -> Color,
        val text: @Composable () -> Color,
    ) {
        SUCCESS(
            { Theme.colors.notificationSuccessBackground },
            { Theme.colors.notificationSuccessText },
        ),
        ERROR(
            { Theme.colors.notificationErrorBackground },
            { Theme.colors.notificationErrorText },
        ),
        INFO(
            { Theme.colors.notificationInfoBackground },
            { Theme.colors.notificationInfoText },
        ),
    }
}
