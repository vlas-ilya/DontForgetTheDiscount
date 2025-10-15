package su.tease.project.feature.preset.presentation.cashback.dialog

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
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.MccCodeItem
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon
import su.tease.project.feature.preset.presentation.component.ShopPresetIconSize

@Composable
fun CashBackInfoDialogContent(
    data: CashBackPresetInfoDialogContentData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (ownerName, ownerIconPreset, cashBackPreset) = data
    Column(
        modifier = modifier.padding(horizontal = Theme.sizes.padding16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                mainContent = {
                    CashBackPresetIcon(
                        name = cashBackPreset.name,
                        iconPreset = cashBackPreset.iconPreset,
                    )
                },
                otherContent = {
                    when (ownerIconPreset) {
                        is BankIconPreset -> BankPresetIcon(
                            iconPreset = ownerIconPreset,
                            name = ownerName,
                            size = BankPresetIconSize.DEFAULT,
                        )

                        is ShopIconPreset -> ShopPresetIcon(
                            iconPreset = ownerIconPreset,
                            name = ownerName,
                            size = ShopPresetIconSize.DEFAULT,
                        )
                    }
                }
            )

            Spacer(Modifier.width(Theme.sizes.padding8))

            DFTextH1(
                text = cashBackPreset.name,
                maxLines = 1,
            )
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        cashBackPreset.info.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFText(
                text = it,
                style = Theme.fonts.placeholder,
            )
        }

        Spacer(Modifier.height(Theme.sizes.padding16))

        cashBackPreset.mccCodes.takeIf { it.isNotEmpty() }?.let { items ->
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFFormElement(
                label = stringResource(R.string.Presets_CashBackInfoDialog_MccList_Label),
                noError = true,
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Theme.sizes.size56)) {
                    items(items = items, key = { it.id }) { MccCodeItem(it.code) }
                }
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFButton(
            label = stringResource(R.string.Presets_CashBackInfoDialog_CloseButton_Label),
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

data class CashBackPresetInfoDialogContentData(
    val ownerName: String,
    val ownerIconPreset: CashBackOwnerIconPreset,
    val cashBackPreset: CashBackPreset,
)
