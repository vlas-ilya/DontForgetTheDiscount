package su.tease.project.feature.cacheback.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

@Composable
fun CacheBackPresetIcon(
    cacheBackPreset: CacheBackPreset,
    size: CacheBackPresetIconSize = CacheBackPresetIconSize.DEFAULT,
) {
    DFImage(
        url = cacheBackPreset.iconPreset.iconUrl,
        contentDescription = stringResource(
            R.string.page_cache_back_list_dialog_cache_back_content_description_icon,
            cacheBackPreset.name,
        ),
        modifier = Modifier
            .clip(CircleShape)
            .background(Theme.colors.inputBackground)
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
