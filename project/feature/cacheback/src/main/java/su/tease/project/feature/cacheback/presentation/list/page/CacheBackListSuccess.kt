package su.tease.project.feature.cacheback.presentation.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.collections.immutable.PersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.list.LazyList
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.cacheback.R

@Composable
fun CacheBackListSuccess(
    list: State<PersistentList<LazyListItem>>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (list.value.isEmpty()) {
            DFTextH1(
                text = stringResource(R.string.page_cache_back_list_placeholder_empty_result),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.sizes.padding20)
                    .padding(horizontal = Theme.sizes.padding8)
                    .align(Alignment.Center),
                color = Theme.colors.accent,
                textAlign = TextAlign.Center,
            )
            return
        }
        LazyList(
            count = list.value.size,
            modifier = Modifier.fillMaxWidth(),
            itemContent = list.value::get,
            verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
            contentPadding = PaddingValues(Theme.sizes.padding8),
        )
    }
}
