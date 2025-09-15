package su.tease.project.feature.bank.presentation.save.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.save.utils.FormFieldError

@Composable
fun SaveBankAccountNameEditText(
    nameState: State<String?>,
    onChange: (String) -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.Bank_SaveBankAccountPage_AccountNameItem_Title),
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.Bank_SaveBankAccountPage_AccountNameItem_Error)
        },
        modifier = modifier,
    ) {
        DFTextField(
            text = nameState.map { it.orEmpty() },
            placeholder = stringResource(R.string.Bank_SaveBankAccountPage_AccountNameItem_Placeholder),
            onChange = { onChange(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLength = NAME_MAX_LENGTH,
        )
    }
}

private const val NAME_MAX_LENGTH = 255
