package su.tease.project.feature.cacheback.presentation.list.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack

@Composable
fun CacheBackInfoDialog(
    info: State<Pair<BankAccount, CacheBack>?>,
    onHide: () -> Unit,
    dateProvider: DateProvider,
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
            dateProvider
        )
    }
}
