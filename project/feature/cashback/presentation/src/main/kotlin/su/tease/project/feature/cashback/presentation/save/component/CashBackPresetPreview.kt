package su.tease.project.feature.cashback.presentation.save.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.cashback.presentation.dependencies.view.Compose

@Composable
fun CashBackPresetPreview(
    cashBackPreset: CashBackPreset,
    cashBackPresetIconView: CashBackPresetIconView,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        cashBackPresetIconView.Compose(cashBackPreset.name, cashBackPreset.iconPreset)
        Spacer(modifier = Modifier.width(Theme.sizes.padding4))
        DFText(
            text = cashBackPreset.name,
            style = Theme.fonts.placeholder,
            maxLines = 1,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
        )
    }
}
