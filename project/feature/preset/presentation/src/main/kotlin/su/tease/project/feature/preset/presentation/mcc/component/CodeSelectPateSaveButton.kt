package su.tease.project.feature.preset.presentation.mcc.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.preset.presentation.R

@Composable
fun CodeSelectPateSaveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.page_select_mcc_codes_button_save),
        onClick = { onClick() },
        modifier = modifier,
    )
}
