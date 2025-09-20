package su.tease.project.feature.shop.presentation.dependencies.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.Shop

@Immutable
interface CashBackInfoDialogView {
    @Composable
    fun ComposeComponent(
        info: State<Pair<Shop, CashBack>?>,
        onHide: () -> Unit,
        dateProvider: DateProvider,
        modifier: Modifier,
    )
}

@Composable
fun CashBackInfoDialogView.Compose(
    info: State<Pair<Shop, CashBack>?>,
    onHide: () -> Unit,
    dateProvider: DateProvider,
    modifier: Modifier = Modifier,
) = ComposeComponent(
    info = info,
    onHide = onHide,
    dateProvider = dateProvider,
    modifier = modifier,
)
