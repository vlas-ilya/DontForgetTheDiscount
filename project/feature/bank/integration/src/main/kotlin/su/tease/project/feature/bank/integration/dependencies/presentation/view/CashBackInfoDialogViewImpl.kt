package su.tease.project.feature.bank.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.map
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBack
import su.tease.project.feature.bank.integration.mapper.cashback.toExternal
import su.tease.project.feature.bank.integration.mapper.preset.toPreset
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.cashback.presentation.dialog.CashBackInfoDialog
import su.tease.project.feature.cashback.presentation.dialog.CashBackInfoDialogContentData
import su.tease.project.feature.preset.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.presentation.component.BankPresetIcon as PresetBankPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon as PresetCashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.MccCodeItem as PresetMccCodeItem

class CashBackInfoDialogViewImpl : CashBackInfoDialogView {

    @Composable
    override fun ComposeComponent(
        info: State<Pair<BankAccount, CashBack>?>,
        onHide: () -> Unit,
        dateProvider: DateProvider,
        modifier: Modifier
    ) {
        CashBackInfoDialog(
            info = info.map { it?.toData() },
            onHide = onHide,
            dateProvider = dateProvider,
            cashBackPresetIconView = @Composable { name, iconPreset ->
                PresetCashBackPresetIcon(
                    name = name,
                    iconPreset = iconPreset.toPreset(),
                    size = CashBackPresetIconSize.DEFAULT,
                )
            },
            cashBackOwnerPresetIconView = @Composable { name, icon ->
                PresetBankPresetIcon(
                    iconPreset = icon.toPreset(),
                    name = name,
                    size = BankPresetIconSize.DEFAULT,
                )
            },
            mccCodeItemView = @Composable { code ->
                PresetMccCodeItem(code = code)
            },
            modifier = modifier,
        )
    }

    private fun Pair<BankAccount, CashBack>.toData() = CashBackInfoDialogContentData(
        ownerName = first.customName,
        ownerIconPreset = first.preset.iconPreset.toExternal(),
        cashBack = second.toExternal(first.id),
    )
}
