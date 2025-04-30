package su.tease.design.component.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.design.component.navigationbar.data.NavigationBarItemData
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.icons.R
import su.tease.project.design.theme.impl.Theme

private fun <T> indexOf(
    selected: T,
    items: PersistentList<NavigationBarItemData<T>>,
    compare: (T, T) -> Boolean = { a, b -> a == b },
): Int? = items.indexOfFirst { compare(selected, it.value) }.takeIf { it != -1 }

@Composable
fun <T> NavigationBar(
    selected: T,
    items: PersistentList<NavigationBarItemData<T>>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    compare: (T, T) -> Boolean = { a, b -> a == b },
) {
    val rememberedCompare by rememberUpdatedState(compare)
    var selectedIndex by remember {
        mutableIntStateOf(indexOf(selected, items, rememberedCompare) ?: 0)
    }

    LaunchedEffect(selected, items) {
        selectedIndex = indexOf(selected, items, rememberedCompare) ?: selectedIndex
    }

    EqualWidthRow(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background0)
            .padding(vertical = Theme.sizes.padding6)
            .padding(horizontal = Theme.sizes.padding4)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            key(item.name) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Theme.sizes.round12))
                        .clickable { onSelect(item.value) }
                        .background(
                            isSelected.choose(
                                Theme.colors.headerText,
                                Theme.colors.background0,
                            )
                        )
                        .padding(
                            horizontal = Theme.sizes.padding10,
                            vertical = Theme.sizes.padding20,
                        )
                ) {
                    Icon(
                        painter = item.image,
                        contentDescription = "",
                        tint = isSelected.choose(
                            Theme.colors.background0,
                            Theme.colors.headerText,
                        ),
                        modifier = Modifier
                            .size(Theme.sizes.size28)
                    )
                    Text(
                        item.name,
                        modifier = Modifier
                            .padding(top = Theme.sizes.padding4),
                        color = isSelected.choose(
                            Theme.colors.background0,
                            Theme.colors.headerText,
                        ),
                        fontSize = Theme.fonts.smallTitle.fontSize,
                        fontStyle = Theme.fonts.smallTitle.fontStyle,
                        fontWeight = Theme.fonts.smallTitle.fontWeight,
                        fontFamily = Theme.fonts.smallTitle.fontFamily,
                    )
                }
            }
        }
    }
}

@Composable
private fun EqualWidthRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val containerWidth = constraints.maxWidth
        val childCount = measurables.size

        val naturalWidths = measurables.map { it.maxIntrinsicWidth(Constraints.Infinity) }
        val naturalHeights = measurables.map { it.maxIntrinsicHeight(Constraints.Infinity) }

        val maxChildWidth = naturalWidths.maxOf { it }
        val maxChildHeight = naturalHeights.maxOf { it }

        val totalItemsWidth = maxChildWidth * childCount
        val remainingSpace = containerWidth - totalItemsWidth

        val spacing = if (childCount > 1 && remainingSpace >= 0) {
            remainingSpace / (childCount * 2)
        } else {
            0
        }

        val fixedWidthConstraints = constraints.copy(
            minWidth = maxChildWidth,
            maxWidth = maxChildWidth
        )

        val placeables = measurables.map { it.measure(fixedWidthConstraints) }

        layout(
            width = containerWidth,
            height = maxChildHeight
        ) {
            var x = 0
            placeables.forEach { placeable ->
                x += spacing
                placeable.placeRelative(x, 0)
                x += maxChildWidth + spacing
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
            name = "Tab3 Tab3",
            image = painterResource(R.drawable.antenna),
        ),
    )

    val data = items.map { it.value }.toPersistentList()

    Theme {
        var selected by remember { mutableIntStateOf(data.first()) }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            NavigationBar(
                selected = selected,
                items = items,
                onSelect = { selected = it }
            )
        }
    }
}
