package su.tease.project.design.component.controls.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@NonSkippableComposable
fun LazyList(
    count: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    itemContent: (index: Int) -> LazyListItem
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
    ) {
        items(
            count = count,
            key = { itemContent(it).key },
            contentType = { itemContent(it)::class },
            itemContent = { itemContent(it).run { Compose() } }
        )
    }
}
