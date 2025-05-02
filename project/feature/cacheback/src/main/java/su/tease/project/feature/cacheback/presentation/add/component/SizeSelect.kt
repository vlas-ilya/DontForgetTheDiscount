package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.edit.DFNumberField
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.presentation.add.utls.FormFieldError

@Composable
fun SizeSelect(
    sizeState: State<CacheBackSize?>,
    error: State<FormFieldError?>,
    onChange: (CacheBackSize) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cache_back_size_title),
        error = runIf(error.value == FormFieldError.INCORRECT_VALUE) {
            stringResource(R.string.item_cache_back_size_error)
        },
        modifier = modifier
    ) {
        DFNumberField(
            text = sizeState.map { it?.percent ?: 0 },
            onChange = { onChange(CacheBackSize(it)) },
            placeholder = stringResource(R.string.item_cache_back_size_placeholder),
            minValue = MIN_PERCENT,
            maxValue = MAX_PERCENT,
            modifier = modifier,
        )
    }
}

private const val MIN_PERCENT = 0
private const val MAX_PERCENT = 100
