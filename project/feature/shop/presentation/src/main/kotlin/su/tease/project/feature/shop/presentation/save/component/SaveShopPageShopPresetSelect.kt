package su.tease.project.feature.shop.presentation.save.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFPlaceholder
import su.tease.project.feature.shop.domain.entity.ShopPreset
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.save.utils.FormFieldError

@Composable
fun SaveShopPageShopPresetSelect(
    shopState: State<ShopPreset?>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    shopPresetIconView: ShopPresetIconView,
    modifier: Modifier = Modifier,
    customName: State<String?>? = null,
) {
    DFFormElement(
        label = stringResource(R.string.Shop_SaveShopPage_ShopPresetSelect_Title),
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.Shop_SaveShopPage_ShopPresetSelect_Error)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
                .clickable { onSelect() }
                .background(Theme.colors.inputBackground)
                .fillMaxWidth()
                .height(Theme.sizes.size48)
                .padding(horizontal = Theme.sizes.padding10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            shopState.value?.let {
                SaveShopPageShopPresetPreview(
                    shopPreset = it,
                    customName = customName?.value,
                    shopPresetIconView = shopPresetIconView,
                )
            } ?: run {
                DFPlaceholder(
                    text = stringResource(R.string.Shop_SaveShopPage_ShopPresetSelect_Placeholder),
                    modifier = Modifier.padding(
                        horizontal = Theme.sizes.padding6,
                        vertical = Theme.sizes.padding6,
                    )
                )
            }
        }
    }
}
