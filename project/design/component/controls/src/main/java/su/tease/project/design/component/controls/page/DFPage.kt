@file:Suppress("Filename")

package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf

@Composable
@Suppress("LongParameterList")
fun DFPageContext.DFPage(
    title: String,
    modifier: Modifier = Modifier,
    hasSystemNavigationBar: Boolean = true,
    @DrawableRes actionIcon: Int? = null,
    floatingButtons: PersistentList<DFPageFloatingButton> = persistentListOf(),
    additionalTitleContent: @Composable () -> Unit = {},
    showBackButton: Boolean = true,
    onActionPress: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val targetValueMax = Theme.sizes.round16.value
    val targetValueMin = 0F
    val targetValue = if (hasSystemNavigationBar) targetValueMax else targetValueMin

    @Suppress("MagicNumber")
    val bottomRound by animateFloatAsState(
        targetValue = targetValue,
        label = "bottomRound",
        animationSpec = keyframes {
            when (targetValue) {
                targetValueMin -> {
                    targetValueMax at 0
                    targetValueMax at (DefaultDurationMillis * 0.9).toInt()
                    0f at DefaultDurationMillis
                }
                targetValueMax -> {
                    0f at 0
                    targetValueMax at (DefaultDurationMillis * 0.1).toInt()
                    targetValueMax at DefaultDurationMillis
                }
            }
        }
    )

    Box(
        modifier = modifier
            .background(Theme.colors.background0)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DFPageTitle(
                title = title,
                additionalTitleContent = additionalTitleContent,
                onBackPress = runIf(showBackButton) { { onBackPress() } },
                onActionPress = onActionPress,
                actionIcon = actionIcon,
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding4)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.sizes.round16,
                            topEnd = Theme.sizes.round16,
                            bottomStart = Dp(bottomRound),
                            bottomEnd = Dp(bottomRound),
                        )
                    )
                    .background(Theme.colors.background1)
                    .fillMaxSize()
            ) {
                content()
            }
        }
        if (floatingButtons.size > 0) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = Theme.sizes.padding20)
                    .padding(bottom = Theme.sizes.padding20)
                    .padding(end = Theme.sizes.padding4)
            ) {
                floatingButtons.forEach {
                    Spacer(Modifier.height(Theme.sizes.padding6))
                    DFPageFloatingButton(data = it)
                }
            }
        }
    }
}

interface DFPageContext {
    fun onBackPress(): Job
}
