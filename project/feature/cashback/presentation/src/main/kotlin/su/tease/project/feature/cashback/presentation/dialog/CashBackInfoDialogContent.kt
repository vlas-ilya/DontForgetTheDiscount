package su.tease.project.feature.cashback.presentation.dialog

import androidx.compose.foundation.layout.Box
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
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.FRACTIONAL_SIZE
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.domain.mapper.toMonthYear
import su.tease.project.feature.cashback.presentation.R

@Composable
fun CashBackInfoDialogContent(
    data: CashBackInfoDialogContentData,
    onClick: () -> Unit,
    dateProvider: DateProvider,
    cashBackPresetIconView: @Composable (name: String, iconPreset: CashBackIconPreset) -> Unit,
    cashBackOwnerPresetIconView: @Composable (name: String, icon: CashBackOwnerIconPreset) -> Unit,
    mccCodeItemView: @Composable (code: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (ownerName, ownerIconPreset, cashBack) = data
    Column(
        modifier = modifier.padding(horizontal = Theme.sizes.padding16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                mainContent = { cashBackPresetIconView(cashBack.preset.name, cashBack.preset.iconPreset) },
                otherContent = { cashBackOwnerPresetIconView(ownerName, ownerIconPreset) }
            )

            Spacer(Modifier.width(Theme.sizes.padding8))

            DFTextH1(
                text = cashBack.preset.name,
                maxLines = 1,
            )
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        cashBack.preset.info.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFText(
                text = it,
                style = Theme.fonts.placeholder,
            )
        }

        Spacer(Modifier.height(Theme.sizes.padding16))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DFText(
                text = stringResource(
                    R.string.CashBack_InfoDialog_SizeItem_Percent,
                    cashBack.size.toPercent(FRACTIONAL_SIZE)
                ),
                maxLines = 1,
                style = Theme.fonts.monospace,
            )
            Spacer(Modifier.width(Theme.sizes.padding8))
            DFText(
                text = dateProvider.toText(cashBack.date.toMonthYear()),
                maxLines = 1,
                style = Theme.fonts.placeholder,
            )
        }
        cashBack.preset.mccCodes.takeIf { it.isNotEmpty() }?.let { items ->
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFFormElement(
                label = stringResource(R.string.CashBack_InfoDialog_MccList_Label),
                noError = true,
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Theme.sizes.size56)) {
                    items(items = items, key = { it.id }) {
                        mccCodeItemView(it.code)
                    }
                }
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFButton(
            label = stringResource(R.string.CashBack_InfoDialog_CloseButton_Label),
            onClick = onClick
        )

        Spacer(Modifier.height(Theme.sizes.padding12))
    }
}

@Composable
private fun Icon(
    mainContent: @Composable () -> Unit,
    otherContent: @Composable () -> Unit,
) {
    Box {
        Box(
            modifier = Modifier.padding(end = Theme.sizes.padding8, bottom = Theme.sizes.padding8)
        ) {
            mainContent()
        }
        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            otherContent()
        }
    }
}

data class CashBackInfoDialogContentData(
    val ownerName: String,
    val ownerIconPreset: CashBackOwnerIconPreset,
    val cashBack: CashBack,
)
