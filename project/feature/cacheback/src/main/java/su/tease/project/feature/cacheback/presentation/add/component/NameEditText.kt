package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.feature.cacheback.domain.entity.CacheBackName

@Composable
fun NameEditText(
    nameState: State<CacheBackName?>,
    onChange: (CacheBackName) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFTextField(
        text = nameState.map { it?.value.orEmpty() },
        onChange = { onChange(CacheBackName(it)) },
        modifier = modifier,
    )
}
