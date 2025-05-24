package su.tease.project.feature.cashback.presentation.save.bank.select.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.api.presentation.component.BankPresetIconSize

@Composable
fun SelectBankAccountBankPresetPreview(
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
