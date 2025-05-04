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
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull

@Composable
fun DFIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color? = null,
    size: DFIconButtonSize = DFIconButtonSize.M,
    tint: Color = LocalContentColor.current,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(size.containerSize())
            .thenIfNotNull(background) { Modifier.background(it) }
            .clickable { onClick() }
    ) {
        DFIcon(
            icon = icon,
            tint = tint,
            modifier = Modifier
                .size(size.imageSize())
                .align(Alignment.Center)
        )
    }
}

enum class DFIconButtonSize(
    val containerSize: @Composable () -> Dp,
    val imageSize: @Composable () -> Dp,
) {
    S({ Theme.sizes.size16 }, { Theme.sizes.size12 }),
    M({ Theme.sizes.size32 }, { Theme.sizes.size20 }),
    L({ Theme.sizes.size40 }, { Theme.sizes.size28 }),
}
