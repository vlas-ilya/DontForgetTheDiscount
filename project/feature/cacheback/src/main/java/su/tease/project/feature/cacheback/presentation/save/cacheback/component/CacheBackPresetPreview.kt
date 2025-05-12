package su.tease.project.feature.cacheback.presentation.save.cacheback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

@Composable
fun CacheBackPresetPreview(
    cacheBackPreset: CacheBackPreset,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DFImage(
            url = cacheBackPreset.iconPreset.iconUrl,
            modifier = Modifier
                .clip(CircleShape)
                .background(Theme.colors.background1)
                .padding(Theme.sizes.padding6)
                .size(Theme.sizes.size22),
            contentDescription = "",
            tint = Theme.colors.iconTint,
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
