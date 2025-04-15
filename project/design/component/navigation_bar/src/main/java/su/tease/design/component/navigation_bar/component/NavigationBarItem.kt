package su.tease.design.component.navigation_bar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import su.tease.design.component.navigation_bar.data.NavigationBarItemData
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose

@Composable
fun <T> NavigationBarItem(
    data: NavigationBarItemData<T>,
    selected: Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable { onSelect(data.value) }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = data.image,
            tint = selected.choose(
                Theme.colors.navigationItemContentSelected,
                Theme.colors.navigationItemContent
            ),
            contentDescription = data.contentDescription,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = data.name,
            color = selected.choose(
                Theme.colors.navigationItemContentSelected,
                Theme.colors.navigationItemContent
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
