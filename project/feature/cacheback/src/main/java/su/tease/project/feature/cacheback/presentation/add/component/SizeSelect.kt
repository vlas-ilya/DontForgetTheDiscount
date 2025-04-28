package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.ext.map
import su.tease.project.design.component.controls.edit.DFNumberField
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize

@Composable
fun SizeSelect(
    sizeState: State<CacheBackSize?>,
    onChange: (CacheBackSize) -> Unit,
    modifier: Modifier = Modifier,
) {
    DFNumberField(
        text = sizeState.map { it?.percent ?: 0 },
        onChange = { onChange(CacheBackSize(it)) },
        minValue = MIN_PERCENT,
        maxValue = MAX_PERCENT,
        modifier = modifier,
    )
}

private const val MIN_PERCENT = 0
private const val MAX_PERCENT = 100