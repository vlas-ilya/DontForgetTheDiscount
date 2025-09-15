package su.tease.project.feature.cashback.presentation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset

@Composable
fun CashBackInfoDialog(
    info: State<CashBackInfoDialogContentData?>,
    onHide: () -> Unit,
    dateProvider: DateProvider,
    cashBackPresetIconView: @Composable (name: String, iconPreset: CashBackIconPreset) -> Unit,
    cashBackOwnerPresetIconView: @Composable (name: String, icon: CashBackOwnerIconPreset) -> Unit,
    mccCodeItemView: @Composable (code: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFBottomSheet(
        data = info.value,
        modifier = modifier,
        onHide = { onHide() }
    ) {
        CashBackInfoDialogContent(
            data = data,
            onClick = ::dismiss,
            dateProvider = dateProvider,
            cashBackPresetIconView = cashBackPresetIconView,
            cashBackOwnerPresetIconView = cashBackOwnerPresetIconView,
            mccCodeItemView = mccCodeItemView,
        )
    }
}
