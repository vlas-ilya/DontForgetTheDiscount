package su.tease.project.feature.preset.impl.presentation.cashback.select.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

@Composable
fun CashBackPresetInfoDialog(
    info: State<Pair<BankPreset, CashBackPreset>?>,
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
