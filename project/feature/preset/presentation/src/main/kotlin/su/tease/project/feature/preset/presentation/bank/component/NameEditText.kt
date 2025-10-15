package su.tease.project.feature.preset.presentation.bank.component

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
import su.tease.project.feature.preset.presentation.bank.save.utils.FormFieldError

@Composable
fun NameEditText(
    nameState: State<String>,
    focusRequester: FocusRequester,
    onChange: (String) -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.Presets_SaveBankPage_Name_Title),
        error = when (error.value) {
            FormFieldError.REQUIRED_BUT_EMPTY -> stringResource(R.string.Presets_SaveBankPage_Name_Error_Common)
            FormFieldError.DUPLICATE -> stringResource(R.string.Presets_SaveBankPage_Name_Error_Duplicate)
            else -> null
        },
        modifier = modifier,
    ) {
        DFTextField(
            text = nameState.map { it },
            placeholder = stringResource(R.string.Presets_SaveBankPage_Name_Placeholder),
            onChange = { onChange(it) },
            maxLength = NAME_MAX_LENGTH,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
    }
}

private const val NAME_MAX_LENGTH = 255
