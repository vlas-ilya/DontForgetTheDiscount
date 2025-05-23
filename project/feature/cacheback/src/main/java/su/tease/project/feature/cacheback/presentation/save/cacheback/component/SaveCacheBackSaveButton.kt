package su.tease.project.feature.cacheback.presentation.save.cacheback.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.feature.cacheback.R

@Composable
fun SaveCacheBackSaveButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFButton(
        label = stringResource(R.string.item_cache_back_button_save),
        onClick = onSubmit,
        modifier = modifier,
    )
}
