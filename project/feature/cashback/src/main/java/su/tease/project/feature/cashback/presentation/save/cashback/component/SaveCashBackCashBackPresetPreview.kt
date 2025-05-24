package su.tease.project.feature.cashback.presentation.save.cashback.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIconSize

@Composable
fun SaveCashBackCashBackPresetPreview(
    cashBackPreset: CashBackPreset,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CashBackPresetIcon(
            cashBackPreset = cashBackPreset,
            size = CashBackPresetIconSize.EXTRA_SMALL,
            background = Theme.colors.background1
        )
        Spacer(modifier = Modifier.width(Theme.sizes.padding4))
        DFText(
            text = cashBackPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
        )
    }
}
