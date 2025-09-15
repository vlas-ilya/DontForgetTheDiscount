package su.tease.project.feature.preset.presentation.cashback.save.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.cashback.save.utils.FormFieldError

@Composable
fun CashBackIconSelect(
    iconState: State<CashBackIconPreset?>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cash_back_icon_title),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .thenIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
                    Modifier.border(
                        width = Theme.sizes.size2,
                        color = Theme.colors.error,
                        shape = CircleShape,
                    )
                }
                .clip(CircleShape)
                .clickable { onSelect() }
                .background(Theme.colors.inputBackground)
                .size(Theme.sizes.size48)
        ) {
            val icon = iconState.value
            DFImage(
                url = icon?.iconUrl ?: ICON_PLACEHOLDER_URL,
                modifier = Modifier
                    .size((icon != null).choose(Theme.sizes.size28, Theme.sizes.size20))
                    .align(Alignment.Center),
                contentDescription = "",
                tint = (icon != null).choose(Theme.colors.iconTint, Theme.colors.inputPlaceholder)
            )
        }
    }
}

private const val ICON_PLACEHOLDER_URL = "https://dontforgetthediscount.ru/static/img/icon/svg/svg/fi-rr-search.svg"
