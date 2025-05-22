package su.tease.project.feature.cacheback.presentation.preset.cacheback.save.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

@Composable
fun MccCodeSelect(
    mccCodesState: State<PersistentList<MccCodePreset>?>,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_select_cache_back_codes_title),
        modifier = modifier,
    ) {
        val selectedCodesText = mccCodesState
            .map { list -> (list?.map { it.code } ?: emptyList()).joinToString() }

        DFTextField(
            text = selectedCodesText,
            placeholder = stringResource(R.string.item_select_cache_back_codes_placeholder),
            onChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelect,
            maxLength = MCC_CODE_MAX_LENGTH,
        )
    }
}

private const val MCC_CODE_MAX_LENGTH = 4
