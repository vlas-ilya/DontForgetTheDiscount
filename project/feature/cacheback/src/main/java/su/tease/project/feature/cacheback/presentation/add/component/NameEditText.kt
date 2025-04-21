package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import su.tease.project.feature.cacheback.domain.entity.CacheBackName

@Composable
fun NameEditText(
    name: State<CacheBackName?>,
    onChange: (CacheBackName) -> Unit,
) {
}
