package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.icon.DFIcon

@Composable
@NonRestartableComposable
fun DFPageFloatingButton(
    data: DFPageFloatingButton,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(Theme.sizes.size40)
            .background(Theme.colors.header)
            .clickable { data.onClick() }
    ) {
        DFIcon(
            icon = data.icon,
            tint = Theme.colors.headerText,
            modifier = Modifier
                .size(Theme.sizes.size20)
                .align(Alignment.Center)
        )
    }
}

data class DFPageFloatingButton(
    @DrawableRes val icon: Int,
    val onClick: Callback,
)
