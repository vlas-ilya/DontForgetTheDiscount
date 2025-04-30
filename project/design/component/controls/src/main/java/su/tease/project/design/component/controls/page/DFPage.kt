package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.core.utils.utils.ComposableContent

@Composable
@Suppress("LongParameterList", "ComposableParamOrder")
fun DFPage(
    title: String,
    modifier: Modifier = Modifier,
    onBackPressed: Callback? = null,
    @DrawableRes actionIcon: Int? = null,
    onActionPressed: Callback? = null,
    floatingButton: DFPageFloatingButton? = null,
    content: ComposableContent,
) {
    Box(
        modifier = modifier
            .background(Theme.colors.background0)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DFPageTitle(
                title = title,
                onBackPressed = onBackPressed,
                onActionPressed = onActionPressed,
                actionIcon = actionIcon,
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Theme.sizes.round10))
                    .background(Theme.colors.background1)
                    .fillMaxSize()
            ) {
                content()
            }
        }
        floatingButton?.let {
            DFPageFloatingButton(
                data = it,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = Theme.sizes.padding20)
                    .padding(bottom = Theme.sizes.padding20)
            )
        }
    }
}
