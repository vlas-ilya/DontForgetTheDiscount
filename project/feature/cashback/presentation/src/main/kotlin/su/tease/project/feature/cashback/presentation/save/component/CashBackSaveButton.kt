package su.tease.project.feature.cashback.presentation.save.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.cashback.presentation.R

@Composable
fun CashBackSaveButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.SaveCashBack_SaveButton_Title),
        onClick = onSubmit,
        modifier = modifier,
    )
}
