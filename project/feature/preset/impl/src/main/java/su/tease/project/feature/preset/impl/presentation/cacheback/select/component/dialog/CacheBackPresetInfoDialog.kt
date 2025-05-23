package su.tease.project.feature.preset.impl.presentation.cacheback.select.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

@Composable
fun CacheBackPresetInfoDialog(
    info: State<Pair<BankPreset, CacheBackPreset>?>,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFBottomSheet(
        data = info.value,
        modifier = modifier,
        onHide = { onHide() }
    ) {
        CacheBackInfoDialogContent(
            data,
            ::dismiss,
        )
    }
}
