package su.tease.project.feature.cashback.presentation.save.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.edit.DFPercentTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cashback.presentation.R
import su.tease.project.feature.cashback.presentation.save.utls.FormFieldError

@Composable
fun CashBackSizeSelect(
    sizeState: State<Int?>,
    error: State<FormFieldError?>,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.SaveCashBack_CashBackSizeItem_Title),
        error = runIf(error.value == FormFieldError.INCORRECT_VALUE) {
            stringResource(R.string.SaveCashBack_CashBackSizeItem_Error)
        },
        modifier = modifier
    ) {
        DFPercentTextField(
            value = sizeState.map { it ?: 0 },
            onChange = { onChange(it) },
            placeholder = stringResource(R.string.SaveCashBack_CashBackSizeItem_Placeholder),
            maxValue = MAX_PERCENT,
        )
    }
}

private const val MAX_PERCENT = 100
