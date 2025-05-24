package su.tease.project.feature.cashback.presentation.list.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.design.component.controls.dialog.DFBottomSheet
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack

@Composable
fun CashBackInfoDialog(
    info: State<Pair<BankAccount, CashBack>?>,
    onHide: () -> Unit,
    dateProvider: DateProvider,
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
            dateProvider
        )
    }
}
