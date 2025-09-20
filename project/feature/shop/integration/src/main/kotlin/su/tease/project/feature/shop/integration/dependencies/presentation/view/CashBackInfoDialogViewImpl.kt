package su.tease.project.feature.shop.integration.dependencies.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.map
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.integration.mapper.cashback.toExternal
import su.tease.project.feature.shop.integration.mapper.preset.toPreset
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.cashback.presentation.dialog.CashBackInfoDialog
import su.tease.project.feature.cashback.presentation.dialog.CashBackInfoDialogContentData
import su.tease.project.feature.preset.presentation.component.ShopPresetIconSize
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon as PresetShopPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon as PresetCashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.MccCodeItem as PresetMccCodeItem

class CashBackInfoDialogViewImpl : CashBackInfoDialogView {

    @Composable
    override fun ComposeComponent(
        info: State<Pair<Shop, CashBack>?>,
        onHide: () -> Unit,
        dateProvider: DateProvider,
        modifier: Modifier
    ) {
        CashBackInfoDialog(
            info = info.map { it?.toData() },
            onHide = onHide,
            dateProvider = dateProvider,
            cashBackPresetIconView = @Composable { name, iconPreset ->
                PresetCashBackPresetIcon(
                    name = name,
                    iconPreset = iconPreset.toPreset(),
                    size = CashBackPresetIconSize.DEFAULT,
                )
            },
            cashBackOwnerPresetIconView = @Composable { name, icon ->
                PresetShopPresetIcon(
                    iconPreset = icon.toPreset(),
                    name = name,
                    size = ShopPresetIconSize.DEFAULT,
                )
            },
            mccCodeItemView = @Composable { code ->
                PresetMccCodeItem(code = code)
            },
            modifier = modifier,
        )
    }

    private fun Pair<Shop, CashBack>.toData() = CashBackInfoDialogContentData(
        ownerName = first.customName,
        ownerIconPreset = first.preset.iconPreset.toExternal(),
        cashBack = second.toExternal(first.id),
    )
}
