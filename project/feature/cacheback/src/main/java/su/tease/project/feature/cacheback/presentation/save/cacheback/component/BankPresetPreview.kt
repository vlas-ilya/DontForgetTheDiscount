package su.tease.project.feature.cacheback.presentation.save.cacheback.component

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
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.BankAccount

@Composable
fun BankPresetPreview(
    bank: BankAccount,
    modifier: Modifier = Modifier,
    roundedIcon: Boolean = false,
    onClick: (() -> Unit)? = null,
) = bank.run {
    Row(
        modifier = modifier
            .thenIfNotNull(onClick) { Modifier.clickable { it() } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        DFImage(
            url = bankPreset.iconPreset.iconUrl,
            modifier = Modifier
                .clip(roundedIcon.choose(CircleShape, RoundedCornerShape(Theme.sizes.round4)))
                .size(Theme.sizes.size32),
            contentDescription = stringResource(
                R.string.item_bank_preview_content_description_bank_logo,
                bankPreset.name,
            )
        )
        DFText(
            text = customName,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
