package su.tease.project.feature.preset.presentation.cashback.save.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.component.ShopPresetIcon
import su.tease.project.feature.preset.presentation.component.ShopPresetIconSize

@Composable
fun CashBackShopPresetPreview(
    shopPreset: ShopPreset,
    modifier: Modifier = Modifier,
    roundedIcon: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .thenIfNotNull(onClick) { Modifier.clickable { it() } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShopPresetIcon(
            iconPreset = shopPreset.iconPreset,
            name = shopPreset.name,
            size = ShopPresetIconSize.DEFAULT,
            clip = roundedIcon.choose(CircleShape, RoundedCornerShape(Theme.sizes.round4))
        )
        DFText(
            text = shopPreset.name,
            style = Theme.fonts.placeholder,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding6)
        )
    }
}
