package su.tease.project.feature.preset.api.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.preset.api.R
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

@Composable
fun CacheBackPresetIcon(
    cacheBackPreset: CacheBackPreset,
    modifier: Modifier = Modifier,
    size: CacheBackPresetIconSize = CacheBackPresetIconSize.DEFAULT,
    background: Color = Theme.colors.inputBackground,
) {
    DFImage(
        url = cacheBackPreset.iconPreset.iconUrl,
        contentDescription = stringResource(
            R.string.item_cache_back_preset_icon_content_description,
            cacheBackPreset.name,
        ),
        modifier = modifier
            .clip(CircleShape)
            .background(background)
            .padding(size.padding())
            .size(size.size())
    )
}

enum class CacheBackPresetIconSize(
    val size: @Composable () -> Dp,
    val padding: @Composable () -> Dp,
) {
    EXTRA_SMALL(
        size = { Theme.sizes.size22 },
        padding = { Theme.sizes.padding6 },
    ),
    SMALL(
        size = { Theme.sizes.size24 },
        padding = { Theme.sizes.padding8 },
    ),
    DEFAULT(
        size = { Theme.sizes.size32 },
        padding = { Theme.sizes.padding14 },
    ),
}
