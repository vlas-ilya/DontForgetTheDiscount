package su.tease.project.feature.cacheback.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIcon
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.icons.R as RIcons

@Composable
fun MccCodeItem(
    code: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasClose: Boolean = false
) {
    Row(
        modifier = modifier
            .padding(Theme.sizes.padding2)
            .clip(RoundedCornerShape(Theme.sizes.round4))
            .clickable { onClick() }
            .background(Theme.colors.inputBackground)
            .padding(Theme.sizes.padding4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = hasClose.choose(
            Arrangement.SpaceBetween,
            Arrangement.Center,
        )
    ) {
        DFText(
            text = code,
            style = Theme.fonts.monospace,
            color = Theme.colors.inputText,
            maxLines = 1,
        )
        runIf(hasClose) {
            DFIcon(
                icon = RIcons.drawable.cross_small,
                modifier = Modifier.size(Theme.sizes.size16),
                tint = Theme.colors.inputPlaceholder,
            )
        }
    }
}
