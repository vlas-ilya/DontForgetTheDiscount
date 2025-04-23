package su.tease.project.design.component.controls.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.icons.R

@Composable
fun DFPageTitle(
    title: String,
    modifier: Modifier = Modifier,
    onBackPressed: Callback,
    onClosePressed: Callback? = null,
) {
    Row(
        modifier = modifier
            .background(Theme.colors.header)
            .fillMaxWidth()
            .height(Theme.sizes.size40),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DFIconButton(
                icon = R.drawable.arrow_small_left,
                modifier = Modifier.padding(start = Theme.sizes.padding4),
                tint = Theme.colors.headerText,
            ) { onBackPressed() }
            DFText(
                text = title,
                maxLines = 1,
                color = Theme.colors.headerText,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding4)
            )
        }
        onClosePressed?.let {
            DFIconButton(
                icon = R.drawable.cross_small,
                modifier = Modifier.padding(end = Theme.sizes.padding4),
                tint = Theme.colors.headerText,
            ) { onClosePressed() }
        }
    }
}
