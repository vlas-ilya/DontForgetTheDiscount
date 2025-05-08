package su.tease.project.design.component.controls.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import su.tease.design.theme.api.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DFBottomSheet(
    data: T?,
    onHide: () -> Unit,
    content: @Composable DFBottomSheetScope<T>.() -> Unit,
) {
    if (data != null) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { true },
        )

        ModalBottomSheet(
            onDismissRequest = onHide,
            sheetState = sheetState,
            modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
        ) {
            val coroutineScope = rememberCoroutineScope()
            val context = remember(this, sheetState) {
                DFBottomSheetScope<T>(this, data, onHide, coroutineScope, sheetState)
            }
            content(context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class DFBottomSheetScope<T>(
    columnScope: ColumnScope,
    val data: T,
    private val onHide: () -> Unit,
    private val coroutineScope: CoroutineScope,
    private val sheetState: SheetState,
) : ColumnScope by columnScope {

    fun dismiss() {
        coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { onHide() }
    }
}