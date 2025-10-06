package su.tease.project.feature.info.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.info.presentation.list.utils.InfoItem

@Composable
fun ListItem(
    infoItem: InfoItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = Theme.sizes.padding20),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Theme.sizes.padding10),
        ) {
            DFText(
                text = stringResource(infoItem.name),
                style = Theme.fonts.text,
            )
        }
        HorizontalDivider(
            color = Theme.colors.placeholder,
            thickness = 1.dp,
        )
    }
}
