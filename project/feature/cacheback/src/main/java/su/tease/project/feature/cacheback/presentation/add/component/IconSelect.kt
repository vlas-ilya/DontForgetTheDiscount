package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFSmallTitle
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

@Composable
fun IconSelect(
    iconState: State<IconPreset?>,
    onSelect: Callback,
    modifier: Modifier = Modifier,
) {
    val icon by iconState

    Column(
        modifier = modifier
            .clickable { onSelect() }
            .padding(vertical = Theme.sizes.padding2)
    ) {
        DFSmallTitle(
            text = stringResource(R.string.icon),
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding4)
                .padding(bottom = Theme.sizes.padding2),
        )
        Box(modifier = Modifier.size(Theme.sizes.size40)) {
            icon?.let {
                DFImage(
                    url = it.url,
                    modifier = Modifier
                        .padding(Theme.sizes.padding4)
                        .size(Theme.sizes.size32),
                    contentDescription = "",
                    tint = Theme.colors.iconTint,
                )
            }
        }
    }
}
