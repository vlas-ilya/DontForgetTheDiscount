package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose

@Composable
@Suppress("LongParameterList")
fun DFPage(
    title: String,
    modifier: Modifier = Modifier,
    hasSystemNavigationBar: Boolean = true,
    @DrawableRes actionIcon: Int? = null,
    floatingButtons: PersistentList<DFPageFloatingButton> = persistentListOf(),
    additionalTitleContent: (@Composable () -> Unit)? = null,
    onBackPress: (() -> Unit)? = null,
    onActionPress: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Theme.colors.background0)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DFPageTitle(
                title = title,
                additionalTitleContent = additionalTitleContent,
                onBackPress = onBackPress,
                onActionPress = onActionPress,
                actionIcon = actionIcon,
            )
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.sizes.round16,
                            topEnd = Theme.sizes.round16,
                            bottomStart = hasSystemNavigationBar.choose(
                                Theme.sizes.round16,
                                Theme.sizes.size0,
                            ),
                            bottomEnd = hasSystemNavigationBar.choose(
                                Theme.sizes.round16,
                                Theme.sizes.size0,
                            ),
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
            ) {
                floatingButtons.forEach {
                    Spacer(Modifier.height(Theme.sizes.padding6))
                    DFPageFloatingButton(data = it)
                }
            }
        }
    }
}
