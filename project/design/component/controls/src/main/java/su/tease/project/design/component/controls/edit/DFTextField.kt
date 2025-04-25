package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import su.tease.design.theme.api.Theme
import su.tease.project.design.theme.impl.utils.Preview

@Composable
fun DFTextField(
    text: State<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text.value,
        onValueChange = { onChange(it) },
        modifier = modifier,
    )
}

@Composable
@Preview
private fun DFTextFieldPreview() = Preview {
    val text = remember { mutableStateOf("") }
    DFTextField(
        text = text,
        onChange = { text.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )
}
