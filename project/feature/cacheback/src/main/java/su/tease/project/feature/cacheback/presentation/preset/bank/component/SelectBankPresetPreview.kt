package su.tease.project.feature.cacheback.presentation.preset.bank.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.presentation.component.BankPresetIcon
import su.tease.project.feature.cacheback.presentation.component.BankPresetIconSize

@Composable
fun SelectBankPresetPreview(
    bankPreset: BankPreset,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .thenIfNotNull(onClick) { Modifier.clickable { it() } }
            .padding(horizontal = Theme.sizes.padding4)
            .padding(
                vertical = Theme.sizes.padding4,
                horizontal = Theme.sizes.padding4,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BankPresetIcon(
            bankPreset = bankPreset,
            size = BankPresetIconSize.DEFAULT,
        )
        DFText(
            text = bankPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
