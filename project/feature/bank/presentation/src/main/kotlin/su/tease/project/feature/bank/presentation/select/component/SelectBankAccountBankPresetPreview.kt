package su.tease.project.feature.bank.presentation.select.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.bank.domain.entity.BankPreset
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.Compose

@Composable
fun SelectBankAccountBankPresetPreview(
    bankPreset: BankPreset,
    bankPresetIconView: BankPresetIconView,
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
        bankPresetIconView.Compose(bankPreset.iconPreset, bankPreset.name)
        DFText(
            text = bankPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
