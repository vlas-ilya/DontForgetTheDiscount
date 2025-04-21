package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize

@Composable
fun SizeSelect(
    size: State<CacheBackSize?>,
    onChange: (CacheBackSize) -> Unit,
) {
}
