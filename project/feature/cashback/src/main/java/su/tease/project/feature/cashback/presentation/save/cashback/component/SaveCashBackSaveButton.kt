package su.tease.project.feature.cashback.presentation.save.cashback.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.cashback.R

@Composable
fun SaveCashBackSaveButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.item_cash_back_button_save),
        onClick = onSubmit,
        modifier = modifier,
    )
}
