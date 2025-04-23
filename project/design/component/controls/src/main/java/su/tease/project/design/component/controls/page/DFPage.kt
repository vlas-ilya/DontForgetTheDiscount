package su.tease.project.design.component.controls.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.core.utils.utils.ComposableContent

@Composable
fun DFPage(
    title: String,
    modifier: Modifier = Modifier,
    onBackPressed: Callback,
    onClosePressed: Callback? = null,
    onRefresh: Callback? = null,
    floatingButton: DFPageFloatingButton? = null,
    content: ComposableContent,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DFPageTitle(
                title = title,
                onBackPressed = onBackPressed,
                onClosePressed = onClosePressed,
            )
            content()
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
