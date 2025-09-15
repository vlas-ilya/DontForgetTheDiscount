package su.tease.project.feature.bank.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import su.tease.project.feature.bank.domain.entity.BankIconPreset
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.preset.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.domain.entity.BankIconPreset as PresetBankIconPreset
import su.tease.project.feature.preset.presentation.component.BankPresetIcon as PresetBankPresetIcon

class BankPresetIconViewImpl : BankPresetIconView {

    @Composable
    override fun ComposeComponent(
        iconPreset: BankIconPreset,
        name: String,
        modifier: Modifier,
        clip: Shape
    ) {
        PresetBankPresetIcon(
            iconPreset = iconPreset.toPreset(),
            name = name,
            size = BankPresetIconSize.DEFAULT,
            modifier = modifier,
            clip = clip,
        )
    }

    private fun BankIconPreset.toPreset() = PresetBankIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
