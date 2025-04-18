package su.tease.design.component.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.design.component.navigationbar.component.NavigationBarItem
import su.tease.design.component.navigationbar.data.NavigationBarItemData
import su.tease.design.theme.api.Theme
import su.tease.project.design.icons.R
import su.tease.project.design.theme.impl.Theme

@Composable
fun <T> NavigationBar(
    selected: T,
    items: PersistentList<NavigationBarItemData<T>>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    compare: (T, T) -> Boolean = { a, b -> a == b },
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Theme.colors.navigationBarBackground)
                .padding(horizontal = 10.dp)
                .align(Alignment.Center),
        ) {
            items.forEach {
                key(it.value) {
                    NavigationBarItem(
                        data = it,
                        selected = compare(it.value, selected),
                        onSelect = onSelect,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun NavigationBarPreview() {
    val items = persistentListOf(
        NavigationBarItemData(
            value = 1,
            name = "Tab1",
            image = painterResource(R.drawable.alarm_clock),
        ),
        NavigationBarItemData(
            value = 2,
            name = "Tab2",
            image = painterResource(R.drawable.apartment),
        ),
        NavigationBarItemData(
            value = 3,
            name = "Tab3",
            image = painterResource(R.drawable.antenna),
        ),
    )

    val data = items.map { it.value }.toPersistentList()

    Theme {
        var selected by remember { mutableIntStateOf(data.first()) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.sizes.pagePadding),
        ) {
            NavigationBar(
                selected = selected,
                items = items,
                onSelect = { selected = it }
            )
        }
    }
}
