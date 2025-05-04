package su.tease.project.design.component.controls.list

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList

@Immutable
interface LazyListItem {
    val key: Any

    @Composable
    fun LazyItemScope.Compose()
}

typealias LazyListItems = PersistentList<LazyListItem>
