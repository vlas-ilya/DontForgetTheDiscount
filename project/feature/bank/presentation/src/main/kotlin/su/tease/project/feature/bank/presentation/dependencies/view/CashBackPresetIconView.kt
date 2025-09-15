package su.tease.project.feature.bank.presentation.dependencies.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.project.feature.bank.domain.entity.CashBackPreset

interface CashBackPresetIconView {
    @Composable
    fun ComposeComponent(
        cashBackPreset: CashBackPreset,
        modifier: Modifier,
    )
}

@Composable
fun CashBackPresetIconView.Compose(
    cashBackPreset: CashBackPreset,
    modifier: Modifier = Modifier,
) = ComposeComponent(
    cashBackPreset = cashBackPreset,
    modifier = modifier,
)
