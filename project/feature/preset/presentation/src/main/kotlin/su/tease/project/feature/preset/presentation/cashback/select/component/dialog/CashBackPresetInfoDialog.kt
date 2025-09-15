package su.tease.project.feature.preset.presentation.cashback.select.component.dialog

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
        data = info.value,
        modifier = modifier,
        onHide = { onHide() }
    ) {
        CashBackInfoDialogContent(
            data,
            ::dismiss,
        )
    }
}
