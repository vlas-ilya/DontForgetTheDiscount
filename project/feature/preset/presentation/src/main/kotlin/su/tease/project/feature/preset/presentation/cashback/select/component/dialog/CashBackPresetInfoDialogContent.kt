package su.tease.project.feature.preset.presentation.cashback.select.component.dialog

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
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.component.CashBackOwnerPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackOwnerPresetIconSize
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.presentation.component.MccCodeItem

@Composable
fun CashBackInfoDialogContent(
    data: Pair<CashBackOwnerPreset, CashBackPreset>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (cashBackOwnerPreset, cashBackPreset) = data
    Column(
        modifier = modifier.padding(horizontal = Theme.sizes.padding8)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CashBackPresetIcon(
                name = cashBackPreset.name,
                iconPreset = cashBackPreset.iconPreset,
                size = CashBackPresetIconSize.DEFAULT,
            )

            Spacer(Modifier.width(Theme.sizes.padding4))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CashBackOwnerPresetIcon(
                    cashBackOwnerPreset = cashBackOwnerPreset,
                    size = CashBackOwnerPresetIconSize.DEFAULT,
                )
                DFText(
                    text = cashBackOwnerPreset.name,
                    style = Theme.fonts.placeholder,
                    modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
                )
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFTextH1(
            text = cashBackPreset.name,
            maxLines = 1
        )

        cashBackPreset.info.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFText(
                text = it,
                style = Theme.fonts.placeholder,
            )
        }

        cashBackPreset.mccCodes.takeIf { it.isNotEmpty() }?.let { items ->
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFFormElement(
                label = stringResource(R.string.dialog_cash_back_preset_codes_label_other),
                noError = true,
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Theme.sizes.size56)) {
                    items(
                        items = items,
                        key = { it.info }
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
            label = stringResource(R.string.dialog_cash_back_preset_info_button_close),
            onClick = onClick
        )

        Spacer(Modifier.height(Theme.sizes.padding12))
    }
}
