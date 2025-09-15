package su.tease.project.feature.preset.presentation.cashback.save.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.cashback.save.utils.FormFieldError

@Composable
fun CashBackOwnerPresetSelect(
    cashBackOwnerPreset: State<CashBackOwnerPreset?>,
    enabled: Boolean,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    when (val value = cashBackOwnerPreset.value) {
        is BankPreset -> CashBackBankPresetSelect(
            bankPreset = value,
            enabled = enabled,
            onSelect = onSelect,
            error = error,
            modifier = modifier,
        )

        is ShopPreset -> CashBackShopPresetSelect(
            shopPreset = value,
            enabled = enabled,
            onSelect = onSelect,
            error = error,
            modifier = modifier,
        )

        null -> Unit
    }
}
