package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo

@Composable
fun InfoEditText(
    infoState: State<CacheBackInfo?>,
    onChange: (CacheBackInfo) -> Unit,
    error: String?,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.info_edit),
        error = error,
    ) {
        DFTextField(
            text = infoState.map { it?.value.orEmpty() },
            onChange = { onChange(CacheBackInfo(it)) },
            modifier = modifier,
        )
    }
}
