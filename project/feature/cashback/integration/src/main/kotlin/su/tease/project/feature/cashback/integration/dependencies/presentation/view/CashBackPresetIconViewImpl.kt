package su.tease.project.feature.cashback.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset as DomainCashBackIconPreset

class CashBackPresetIconViewImpl : CashBackPresetIconView {

    @Composable
    override fun ComposeComponent(
        name: String,
        iconPreset: CashBackIconPreset,
        modifier: Modifier,
    ) {
        CashBackPresetIcon(
            name = name,
            iconPreset = iconPreset.toPreset(),
            size = CashBackPresetIconSize.EXTRA_SMALL,
            background = Theme.colors.background2,
            modifier = modifier,
        )
    }

    private fun CashBackIconPreset.toPreset() = DomainCashBackIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
