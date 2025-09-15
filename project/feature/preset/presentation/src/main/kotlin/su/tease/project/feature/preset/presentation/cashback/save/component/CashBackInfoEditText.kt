package su.tease.project.feature.preset.presentation.cashback.save.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.preset.presentation.R

@Composable
fun CashBackInfoEditText(
    infoState: State<String?>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cash_back_info_title),
        modifier = modifier,
    ) {
        DFTextField(
            text = infoState.map { it.orEmpty() },
            placeholder = stringResource(R.string.item_cash_back_info_placeholder),
            onChange = { onChange(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLength = INFO_MAX_LENGTH,
        )
    }
}

private const val INFO_MAX_LENGTH = 255
