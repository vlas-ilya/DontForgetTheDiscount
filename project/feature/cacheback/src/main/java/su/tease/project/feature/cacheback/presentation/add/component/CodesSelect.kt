package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode

@Composable
fun CodesSelect(
    codesState: State<PersistentList<CacheBackCode>?>,
    onSelect: () -> Unit,
) {
}
