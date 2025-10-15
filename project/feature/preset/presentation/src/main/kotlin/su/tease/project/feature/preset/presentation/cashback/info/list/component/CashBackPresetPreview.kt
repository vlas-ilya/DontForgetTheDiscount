package su.tease.project.feature.preset.presentation.cashback.info.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.presentation.cashback.info.list.reducer.CashBackPresetInfoDialogAction
import su.tease.project.feature.preset.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon
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
        Box {
            CashBackPresetIcon(
                name = cashBackPreset.name,
                iconPreset = cashBackPreset.iconPreset,
                size = CashBackPresetIconSize.SMALL,
                modifier = Modifier.padding(Theme.sizes.padding4)
            )
            val ownerIcon = cashBackPreset.cashBackOwnerPreset.iconPreset
            val ownerName = cashBackPreset.cashBackOwnerPreset.name
            val ownerIconModifier = Modifier
                .align(Alignment.BottomEnd)
                .size(Theme.sizes.size16)
            when (ownerIcon) {
                is BankIconPreset -> BankPresetIcon(ownerIcon, ownerName, ownerIconModifier)
                is ShopIconPreset -> ShopPresetIcon(ownerIcon, ownerName, ownerIconModifier)
            }
        }

        Spacer(modifier = Modifier.width(Theme.sizes.padding4))

        DFText(
            text = cashBackPreset.name,
            style = Theme.fonts.text,
            maxLines = 1,
            modifier = Modifier
                .weight(1F)
                .fillMaxSize(),
        )
        runIf(cashBackPreset.info.isNotBlank() || cashBackPreset.mccCodes.isNotEmpty()) {
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            DFIconButton(
                icon = RIcons.drawable.comment_info,
                size = DFIconButtonSize.S,
                onClick = {
                    store.dispatcher.dispatch(
                        CashBackPresetInfoDialogAction.OnShow(
                            cashBackPreset.cashBackOwnerPreset to cashBackPreset
                        )
                    )
                }
            )
        }
    }
}
