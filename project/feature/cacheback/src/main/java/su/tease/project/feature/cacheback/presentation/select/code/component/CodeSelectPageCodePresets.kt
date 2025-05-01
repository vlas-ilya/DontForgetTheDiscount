package su.tease.project.feature.cacheback.presentation.select.code.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R

@Composable
fun CodeSelectPageCodePresets(
    codePresets: SnapshotStateList<String>,
    selectedCodes: SnapshotStateList<String>,
    code: MutableState<String>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val actualCodePresets by remember {
        derivedStateOf {
            codePresets
                .filter { it !in selectedCodes }
                .filter { it.contains(code.value) }
        }
    }

    DFFormElement(
        label = stringResource(R.string.page_select_cache_back_codes_label_other),
        noError = true,
        modifier = modifier,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = Theme.sizes.size50),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = actualCodePresets,
                key = { it }
            ) {
                CodeItem(
                    code = it,
                    onClick = { onItemClick(it) }
                )
            }
        }
    }
}