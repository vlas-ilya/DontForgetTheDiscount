package su.tease.project.feature.preset.presentation.mcc.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.component.controls.edit.DFNumberField
import su.tease.project.design.component.controls.edit.DFTextFieldAction
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.preset.presentation.R

@Composable
@Suppress("MutableStateParam")
fun CodeSelectPageInput(
    code: MutableState<String>,
    focusRequester: FocusRequester,
    maxMccCode: Int,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        modifier = modifier,
        label = stringResource(R.string.page_select_cash_back_codes_label_input),
    ) {
        Box {
            DFNumberField(
                text = code.map { it.toIntSafe() },
                onChange = { code.value = it.toString() },
                minValue = 0,
                maxValue = maxMccCode,
                action = DFTextFieldAction(
                    icon = su.tease.project.design.icons.R.drawable.plus,
                    onClick = { onActionClick() },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
        }
    }
}
