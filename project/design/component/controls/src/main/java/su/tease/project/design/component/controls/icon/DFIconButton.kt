package su.tease.project.design.component.controls.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.core.utils.utils.Callback

@Composable
fun DFIconButton(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    background: Color? = null,
    tint: Color = LocalContentColor.current,
    onClick: Callback,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(Theme.sizes.size32)
            .thenIf(background != null) { Modifier.background(background!!) }
            .clickable { onClick() }
    ) {
        DFIcon(
            icon = icon,
            tint = tint,
            modifier = Modifier
                .size(Theme.sizes.size20)
                .align(Alignment.Center)
        )
    }
}
