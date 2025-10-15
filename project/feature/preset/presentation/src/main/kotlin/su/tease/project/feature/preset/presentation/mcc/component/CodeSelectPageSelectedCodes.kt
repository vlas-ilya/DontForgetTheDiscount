package su.tease.project.feature.preset.presentation.mcc.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.component.MccCodeItem

@Composable
fun CodeSelectPageSelectedCodes(
    selectedCodes: SnapshotStateList<String>,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        modifier = modifier,
        label = stringResource(R.string.Preset_SelectMccCodePresetPage_SelectedCodes_Label),
        noError = selectedCodes.isNotEmpty(),
    ) {
        if (selectedCodes.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Theme.sizes.size60),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = selectedCodes,
                    key = { it }
                ) {
                    MccCodeItem(
                        code = it,
                        hasClose = true,
                        onClick = { selectedCodes.remove(it) }
                    )
                }
            }
            Spacer(Modifier.height(Theme.sizes.size4))
        } else {
            DFText(
                text = stringResource(R.string.Preset_SelectMccCodePresetPage_SelectedCodes_Placeholder),
                modifier = Modifier.padding(horizontal = Theme.sizes.padding4),
                style = Theme.fonts.placeholder,
                color = Theme.colors.placeholder
            )
        }
    }
}
