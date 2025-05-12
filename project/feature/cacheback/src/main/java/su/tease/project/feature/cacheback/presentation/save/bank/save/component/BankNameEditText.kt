package su.tease.project.feature.cacheback.presentation.save.bank.save.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.presentation.save.bank.save.utils.FormFieldError

@Composable
fun BankNameEditText(
    nameState: State<String?>,
    onChange: (String) -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cache_back_name_title),
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.item_bank_account_name_error)
        },
        modifier = modifier,
    ) {
        DFTextField(
            text = nameState.map { it.orEmpty() },
            placeholder = stringResource(R.string.item_bank_account_name_placeholder),
            onChange = { onChange(it) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
