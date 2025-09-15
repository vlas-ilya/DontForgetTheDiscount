package su.tease.project.feature.cashback.presentation.dependencies.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset

interface CashBackOwnerPreviewView {

    @Composable
    fun ComposeIconComponent(
        name: String,
        icon: CashBackOwnerIconPreset,
        modifier: Modifier,
    )

    fun pageOnSelect(): NavigationTarget.Page
}

@Composable
fun CashBackOwnerPreviewView.ComposeIcon(
    name: String,
    icon: CashBackOwnerIconPreset,
    modifier: Modifier = Modifier,
) = ComposeIconComponent(
    name = name,
    icon = icon,
    modifier = modifier,
)
