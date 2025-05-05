package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.icon.DFIcon

@Composable
@NonRestartableComposable
fun DFPageFloatingButton(
    data: DFPageFloatingButton,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = data.isVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        val hasShadow by remember {
            derivedStateOf {
                transition.currentState == EnterExitState.Visible &&
                    transition.targetState == EnterExitState.Visible
            }
        }
        val animateElevation = animateFloatAsState(
            targetValue = if (hasShadow) Theme.sizes.elevation10.value else 0F,
            label = "elevation"
        )
        Box(
            modifier = Modifier
                .thenIf(hasShadow) {
                    Modifier.shadow(elevation = Dp(animateElevation.value), shape = CircleShape)
                }
                .clip(CircleShape)
                .size(Theme.sizes.size50)
                .background(data.type.background())
                .clickable { data.onClick() }
        ) {
            DFIcon(
                icon = data.icon,
                tint = data.type.tint(),
                modifier = Modifier
                    .size(Theme.sizes.size20)
                    .align(Alignment.Center)
            )
        }
    }
}

@Immutable
data class DFPageFloatingButton(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
    val type: Type = Type.ACCENT,
    val isVisible: Boolean = true,
) {
    enum class Type(
        val background: @Composable () -> Color,
        val tint: @Composable () -> Color,
    ) {
        ACCENT(
            { Theme.colors.buttonBackground },
            { Theme.colors.buttonText },
        ),
        GRAY(
            { Theme.colors.inputBackground },
            { Theme.colors.inputText },
        ),
    }
}
