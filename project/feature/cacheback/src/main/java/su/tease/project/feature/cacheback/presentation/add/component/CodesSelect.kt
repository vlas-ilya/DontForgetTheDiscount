package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode

@Composable
fun CodesSelect(
    codesState: State<PersistentList<CacheBackCode>?>,
    onSelect: Callback,
    error: String?,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_select_cache_back_codes_title),
        error = error,
        modifier = modifier,
    ) {
        val selectedCodesText = codesState
            .map { list -> (list?.map { it.code.value } ?: emptyList()).joinToString() }

        DFTextField(
            text = selectedCodesText,
            placeholder = stringResource(R.string.item_select_cache_back_codes_placeholder),
            onChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelect,
        )
    }

}
