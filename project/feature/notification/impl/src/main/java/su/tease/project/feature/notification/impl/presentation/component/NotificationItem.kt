package su.tease.project.feature.notification.impl.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.icons.R
import su.tease.project.feature.notification.api.Notification

@Composable
fun NotificationItem(
    notification: Notification,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    shouldBeDismissed: Boolean,
) {
    val isVisible = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    LaunchedEffect(Unit) {
        delay(SHOW_NOTIFICATION_TIMEOUT)
        if (!isVisible.value) return@LaunchedEffect
        isVisible.value = false
        delay(HIDE_NOTIFICATION_ANIMATION)
        onDismiss()
    }

    LaunchedEffect(shouldBeDismissed) {
        if (shouldBeDismissed) {
            if (!isVisible.value) return@LaunchedEffect
            isVisible.value = false
            delay(HIDE_NOTIFICATION_ANIMATION)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.sizes.round12))
                .clickable { onClick() }
                .background(notification.type.background())
                .padding(Theme.sizes.padding8)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = Theme.sizes.padding4)
            ) {
                notification.title?.let {
                    DFText(
                        text = it,
                        maxLines = 1,
                        color = notification.type.text()
                    )
                }
                notification.text?.let {
                    DFText(
                        text = it,
                        color = notification.type.text()
                    )
                }
            }
            runIf(notification.closable) {
                Spacer(Modifier.height(Theme.sizes.padding8))
                DFIconButton(
                    icon = R.drawable.cross_small,
                    tint = notification.type.text(),
                    onClick = {
                        scope.launch {
                            if (!isVisible.value) return@launch
                            isVisible.value = false
                            delay(HIDE_NOTIFICATION_ANIMATION)
                            onDismiss()
                        }
                    },
                )
            }
        }
    }
}

private const val HIDE_NOTIFICATION_ANIMATION = 300L
private const val SHOW_NOTIFICATION_TIMEOUT = 5000L
