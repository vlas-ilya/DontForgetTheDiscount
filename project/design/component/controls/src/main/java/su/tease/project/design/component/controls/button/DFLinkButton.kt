package su.tease.project.design.component.controls.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText

@Composable
fun DFLinkButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    DFText(
        text = label.uppercase(),
        color = Theme.colors.link,
        style = Theme.fonts.link,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
            .thenIfNotNull(onClick) { Modifier.clickable { it() } }
            .padding(
                vertical = Theme.sizes.padding6,
                horizontal = Theme.sizes.padding10
            )
    )
}
