package su.tease.project.feature.shop.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.project.feature.shop.domain.entity.CashBackIconPreset
import su.tease.project.feature.shop.domain.entity.CashBackPreset
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset as PresetCashBackIconPreset
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon as PresetCashBackPresetIcon

class CashBackPresetIconViewImpl : CashBackPresetIconView {

    @Composable
    override fun ComposeComponent(
        cashBackPreset: CashBackPreset,
        modifier: Modifier
    ) {
        PresetCashBackPresetIcon(
            name = cashBackPreset.name,
            iconPreset = cashBackPreset.iconPreset.toPreset(),
            modifier = modifier,
            size = CashBackPresetIconSize.SMALL,
        )
    }

    private fun CashBackIconPreset.toPreset() = PresetCashBackIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
