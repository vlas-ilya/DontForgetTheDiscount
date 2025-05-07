package su.tease.project.design.component.controls.dropdown

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.text.DFPlaceholder

@Composable
fun <T> DFDropdownMenu(
    selected: T?,
    items: PersistentList<T>,
    text: (T) -> String,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int? = null,
    background: Color = Theme.colors.inputBackground,
    clip: Shape = RoundedCornerShape(Theme.sizes.roundInfinity),
) {
    val expanded = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clip(clip)
            .clickable { expanded.value = !expanded.value }
            .background(background)
            .fillMaxWidth()
            .height(Theme.sizes.size48)
            .padding(horizontal = Theme.sizes.padding10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DFPlaceholder(
            text = selected?.let(text)
                ?: placeholder?.let { stringResource(it) }
                ?: "",
            modifier = Modifier
                .padding(
                    horizontal = Theme.sizes.padding6,
                    vertical = Theme.sizes.padding6,
                ),
            color = (selected != null).choose(
                Theme.colors.inputText,
                Theme.colors.inputPlaceholder,
            )
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = {
                        DFPlaceholder(
                            text = text(it),
                            modifier = Modifier.padding(
                                horizontal = Theme.sizes.padding6,
                                vertical = Theme.sizes.padding6,
                            ),
                            color = Theme.colors.inputText
                        )
                    },
                    onClick = {
                        onItemClick(it)
                        expanded.value = false
                    },
                )
            }
        }
    }
}
