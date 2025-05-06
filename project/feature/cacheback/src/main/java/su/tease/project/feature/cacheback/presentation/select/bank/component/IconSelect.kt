package su.tease.project.feature.cacheback.presentation.select.bank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.presentation.select.bank.utils.FormFieldError

@Composable
fun IconSelect(
    iconState: State<IconPreset?>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_add_bank_icon_title),
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
            val icon by iconState
            icon?.let {
                DFImage(
                    url = it.url,
                    modifier = Modifier
                        .size(Theme.sizes.size48)
                        .align(Alignment.Center),
                    contentDescription = "",
                )
            }
        }
    }
}
