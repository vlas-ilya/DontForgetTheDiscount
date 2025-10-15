package su.tease.project.feature.preset.presentation.cashback.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset

@Composable
fun CashBackPresetInfoDialog(
    info: State<Pair<CashBackOwnerPreset, CashBackPreset>?>,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFBottomSheet(
        data = info.value?.toData(),
        modifier = modifier,
        onHide = { onHide() }
    ) {
        CashBackInfoDialogContent(
            data = data,
            onClick = ::dismiss,
        )
    }
}

private fun Pair<CashBackOwnerPreset, CashBackPreset>.toData() =
    CashBackPresetInfoDialogContentData(
        ownerName = first.name,
        ownerIconPreset = first.iconPreset,
        cashBackPreset = second,
    )