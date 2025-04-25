package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo

@Composable
fun InfoEditText(
    info: State<CacheBackInfo?>,
    onChange: (CacheBackInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFTextField(
        text = info.map { it?.value.orEmpty() },
        onChange = { onChange(CacheBackInfo(it)) },
        modifier = modifier,
    )
}
