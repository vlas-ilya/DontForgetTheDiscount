package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.cacheback.R

@Composable
fun SaveButton(
    onSubmit: Callback,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.page_add_cache_back_button_save),
        onClick = onSubmit
    )
}
