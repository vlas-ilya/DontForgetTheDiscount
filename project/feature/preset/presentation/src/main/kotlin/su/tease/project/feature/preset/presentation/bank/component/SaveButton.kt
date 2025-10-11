package su.tease.project.feature.preset.presentation.bank.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.preset.presentation.R

@Composable
fun SaveButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.Presets_SaveBankPage_SaveButton_Label),
        onClick = onSubmit,
        modifier = modifier,
    )
}
