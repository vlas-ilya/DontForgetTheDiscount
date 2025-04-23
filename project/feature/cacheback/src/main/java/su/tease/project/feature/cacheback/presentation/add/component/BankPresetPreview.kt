package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

@Composable
fun BankPresetPreview(
    bank: BankPreset,
    onClick: Callback,
) = bank.run {
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        DFImage(
            url = icon.url,
            modifier = Modifier
                .padding(start = Theme.sizes.padding4)
                .clip(RoundedCornerShape(Theme.sizes.round4))
                .size(Theme.sizes.size24)
                .background(Theme.colors.shimmer1),
            contentDescription = stringResource(R.string.bank_content_description, name)
        )
        DFText(
            text = name,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding4)
        )
    }
}
