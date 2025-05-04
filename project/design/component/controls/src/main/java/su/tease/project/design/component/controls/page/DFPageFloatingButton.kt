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
import androidx.compose.ui.draw.shadow
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.icon.DFIcon

@Composable
@NonRestartableComposable
fun DFPageFloatingButton(
    data: DFPageFloatingButton,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(elevation = Theme.sizes.elevation10, shape = CircleShape)
            .clip(CircleShape)
            .size(Theme.sizes.size50)
            .background(Theme.colors.buttonBackground)
            .clickable { data.onClick() }
    ) {
        DFIcon(
            icon = data.icon,
            tint = Theme.colors.buttonText,
            modifier = Modifier
                .size(Theme.sizes.size20)
                .align(Alignment.Center)
        )
    }
}

data class DFPageFloatingButton(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
)
