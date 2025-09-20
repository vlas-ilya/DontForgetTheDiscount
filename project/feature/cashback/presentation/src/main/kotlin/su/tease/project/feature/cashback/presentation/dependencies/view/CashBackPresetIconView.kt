package su.tease.project.feature.cashback.presentation.dependencies.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset

@Immutable
interface CashBackPresetIconView {

    @Composable
    fun ComposeComponent(
        name: String,
        iconPreset: CashBackIconPreset,
        modifier: Modifier,
    )
}

@Composable
fun CashBackPresetIconView.Compose(
    name: String,
    iconPreset: CashBackIconPreset,
    modifier: Modifier = Modifier,
) = ComposeComponent(
    name = name,
    iconPreset = iconPreset,
    modifier = modifier,
)
