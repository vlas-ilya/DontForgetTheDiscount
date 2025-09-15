package su.tease.project.feature.bank.presentation.dependencies.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBack

interface CashBackInfoDialogView {
    @Composable
    fun ComposeComponent(
        info: State<Pair<BankAccount, CashBack>?>,
        onHide: () -> Unit,
        dateProvider: DateProvider,
        modifier: Modifier,
    )
}

@Composable
fun CashBackInfoDialogView.Compose(
    info: State<Pair<BankAccount, CashBack>?>,
    onHide: () -> Unit,
    dateProvider: DateProvider,
    modifier: Modifier = Modifier,
) = ComposeComponent(
    info = info,
    onHide = onHide,
    dateProvider = dateProvider,
    modifier = modifier,
)
