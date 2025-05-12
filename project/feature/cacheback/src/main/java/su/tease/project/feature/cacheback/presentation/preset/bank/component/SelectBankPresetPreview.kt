package su.tease.project.feature.cacheback.presentation.preset.bank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset

@Composable
fun SelectBankPresetPreview(
    name: String,
    iconPreset: BankIconPreset,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
            .thenIfNotNull(onClick) { Modifier.clickable { it() } }
            .padding(
                vertical = Theme.sizes.padding4,
                horizontal = Theme.sizes.padding4,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DFImage(
            url = iconPreset.iconUrl,
            modifier = Modifier
                .clip(CircleShape)
                .size(Theme.sizes.size32)
                .background(Theme.colors.tmpFiller),
            contentDescription = stringResource(
                R.string.item_bank_preview_content_description_bank_logo,
                name,
            )
        )
        DFText(
            text = name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
