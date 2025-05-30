package su.tease.project.feature.preset.impl.presentation.cashback.select.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.CashBackPresetInfoDialogAction
import su.tease.project.design.icons.R as RIcons

@Composable
fun CashBackPresetPreview(
    store: Store<*>,
    cashBackPreset: CashBackPreset,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = Theme.sizes.padding8)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CashBackPresetIcon(
            cashBackPreset = cashBackPreset,
            size = CashBackPresetIconSize.SMALL,
        )

        Spacer(modifier = Modifier.width(Theme.sizes.padding4))

        DFText(
            text = cashBackPreset.name,
            style = Theme.fonts.text,
            modifier = Modifier
                .weight(1F)
                .fillMaxSize(),
        )
        runIf(
            cashBackPreset.info.isNotBlank() ||
                cashBackPreset.mccCodes.isNotEmpty()
        ) {
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            DFIconButton(
                icon = RIcons.drawable.comment_info,
                size = DFIconButtonSize.S,
                onClick = {
                    store.dispatcher.dispatch(
                        CashBackPresetInfoDialogAction.OnShow(
                            cashBackPreset.bankPreset to cashBackPreset
                        )
                    )
                }
            )
        }
    }
}
