package su.tease.project.feature.preset.impl.presentation.cacheback.save.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.R

@Composable
fun CacheBackMccCodeSelect(
    mccCodesState: State<PersistentList<MccCodePreset>?>,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cache_back_mcc_codes_title),
        modifier = modifier,
    ) {
        val selectedCodesText = mccCodesState
            .map { list -> (list?.map { it.code } ?: emptyList()).joinToString() }

        DFTextField(
            text = selectedCodesText,
            placeholder = stringResource(R.string.item_cache_back_mcc_codes_placeholder),
            onChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelect,
            maxLength = MCC_CODE_MAX_LENGTH,
        )
    }
}

private const val MCC_CODE_MAX_LENGTH = 4
