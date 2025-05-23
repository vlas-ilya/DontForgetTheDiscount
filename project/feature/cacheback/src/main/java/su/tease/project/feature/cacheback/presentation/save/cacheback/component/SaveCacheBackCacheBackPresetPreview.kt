package su.tease.project.feature.cacheback.presentation.save.cacheback.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.api.presentation.component.CacheBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.CacheBackPresetIconSize
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

@Composable
fun SaveCacheBackCacheBackPresetPreview(
    cacheBackPreset: CacheBackPreset,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CacheBackPresetIcon(
            cacheBackPreset = cacheBackPreset,
            size = CacheBackPresetIconSize.EXTRA_SMALL,
            background = Theme.colors.background1
        )
        Spacer(modifier = Modifier.width(Theme.sizes.padding4))
        DFText(
            text = cacheBackPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
        )
    }
}
