package su.tease.project.feature.cashback.presentation.list.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.toPercent
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.FRACTIONAL_SIZE
import su.tease.project.feature.cashback.domain.mapper.toMonthYear
import su.tease.project.feature.preset.api.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.api.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.MccCodeItem

@Composable
fun CashBackInfoDialogContent(
    data: Pair<BankAccount, CashBack>,
    onClick: () -> Unit,
    dateProvider: DateProvider,
    modifier: Modifier = Modifier,
) {
    val (bank, cashBack) = data
    Column(
        modifier = modifier.padding(horizontal = Theme.sizes.padding8)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CashBackPresetIcon(
                cashBackPreset = cashBack.cashBackPreset,
            )

            Spacer(Modifier.width(Theme.sizes.padding4))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BankPresetIcon(
                    bankPreset = bank.bankPreset,
                    size = BankPresetIconSize.DEFAULT,
                )
                DFText(
                    text = bank.customName,
                    style = Theme.fonts.placeholder,
                    modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
                )
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFTextH1(
            text = cashBack.cashBackPreset.name,
            maxLines = 1,
        )

        cashBack.cashBackPreset.info.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFText(
                text = it,
                style = Theme.fonts.placeholder,
            )
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DFText(
                text = stringResource(
                    R.string.dialog_cash_back_info_item_size_percent,
                    cashBack.size.toPercent(FRACTIONAL_SIZE)
                ),
                style = Theme.fonts.monospace,
            )
            Spacer(Modifier.width(Theme.sizes.padding8))
            DFText(
                text = dateProvider.toText(cashBack.date.toMonthYear()),
                style = Theme.fonts.placeholder,
            )
        }
        cashBack.cashBackPreset.mccCodes.takeIf { it.isNotEmpty() }?.let { items ->
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFFormElement(
                label = stringResource(R.string.dialog_cash_back_info_item_mcc_codes_label),
                noError = true,
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Theme.sizes.size56)) {
                    items(
                        items = items,
                        key = { it.id }
                    ) {
                        MccCodeItem(
                            code = it.code,
                            onClick = { }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFButton(
            label = stringResource(R.string.dialog_cash_back_info_item_button_close),
            onClick = onClick
        )

        Spacer(Modifier.height(Theme.sizes.padding12))
    }
}
