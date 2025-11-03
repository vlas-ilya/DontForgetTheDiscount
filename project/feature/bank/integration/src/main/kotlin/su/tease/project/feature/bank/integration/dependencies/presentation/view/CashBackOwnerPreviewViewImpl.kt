package su.tease.project.feature.bank.integration.dependencies.presentation.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.project.feature.bank.integration.mapper.preset.toPreset
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.save.reducer.SaveCashBackReducer
import su.tease.project.feature.preset.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.presentation.component.BankPresetIcon as PresetBankPresetIcon

class CashBackOwnerPreviewViewImpl : CashBackOwnerPreviewView {

    @Composable
    override fun ComposeIconComponent(
        name: String,
        icon: CashBackOwnerIconPreset,
        modifier: Modifier,
    ) {
        PresetBankPresetIcon(
            iconPreset = icon.toPreset(),
            name = name,
            size = BankPresetIconSize.DEFAULT,
            clip = CircleShape,
            modifier = modifier,
        )
    }
}
