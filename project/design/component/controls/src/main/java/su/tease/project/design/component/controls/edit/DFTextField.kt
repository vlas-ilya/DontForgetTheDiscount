package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.theme.impl.utils.Preview

@Composable
fun DFTextField(
    text: State<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    val isFocused = remember { mutableStateOf(false) }

    TextField(
        value = text.value,
        onValueChange = { onChange(it) },
        placeholder = { Text(placeholder) },
        modifier = modifier
            .border(
                isFocused.value.choose(
                    Theme.sizes.size1,
                    Theme.sizes.size2,
                ),
                Theme.colors.accent4,
                RoundedCornerShape(Theme.sizes.round4)
            )
            .onFocusChanged {
                isFocused.value = !it.isFocused
            },
        shape = RoundedCornerShape(Theme.sizes.round4),
        colors = TextFieldDefaults.colors().copy(
            cursorColor = Theme.colors.text,

            focusedContainerColor = Theme.colors.background,
            unfocusedContainerColor = Theme.colors.background,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            focusedTextColor = Theme.colors.text,
            unfocusedTextColor = Theme.colors.text,

            focusedPlaceholderColor = Theme.colors.accent3,
            unfocusedPlaceholderColor = Theme.colors.accent3,
        )
    )
}

@Composable
@Preview
private fun DFTextFieldPreview() = Preview {
    val text = remember { mutableStateOf("") }
    DFTextField(
        text = text,
        onChange = { text.value = it },
        placeholder = "Test",
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )
    DFTextField(
        text = text,
        onChange = { text.value = it },
        placeholder = "Test",
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )
}
