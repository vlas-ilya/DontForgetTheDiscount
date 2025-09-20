package su.tease.project.feature.preset.presentation.shop.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.shop.save.utils.FormFieldError

@Composable
fun NameEditText(
    nameState: State<String>,
    focusRequester: FocusRequester,
    onChange: (String) -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_shop_name_title),
        error = when (error.value) {
            FormFieldError.REQUIRED_BUT_EMPTY -> stringResource(R.string.item_shop_name_error_common)
            FormFieldError.DUPLICATE -> stringResource(R.string.item_shop_name_error_duplicate)
            else -> null
        },
        modifier = modifier,
    ) {
        DFTextField(
            text = nameState.map { it },
            placeholder = stringResource(R.string.item_shop_name_placeholder),
            onChange = { onChange(it) },
            maxLength = NAME_MAX_LENGTH,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
    }
}

private const val NAME_MAX_LENGTH = 255
