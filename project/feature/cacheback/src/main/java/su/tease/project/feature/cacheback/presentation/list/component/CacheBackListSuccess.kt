package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank

@Composable
fun CacheBackListSuccess(
    list: State<PersistentList<CacheBackBank>>,
    onRefresh: () -> Unit,
) {
}
