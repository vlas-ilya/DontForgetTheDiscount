package su.tease.project.feature.cacheback.presentation.save.bank.save.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.cacheback.R

@Composable
fun SaveBankAccountSaveButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.item_bank_account_button_save),
        onClick = onSubmit,
        modifier = modifier,
    )
}
