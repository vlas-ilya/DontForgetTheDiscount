package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.theme.impl.utils.Preview
import kotlin.math.max
import kotlin.math.min

@Composable
fun DFNumberField(
    text: State<Int>,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    showZero: Boolean = false,
    placeholder: String = "",
) {
    val isFocused = remember { mutableStateOf(false) }

    TextField(
        value = text.value.toString()
            .let { if (showZero || it != "0") it else "" },
        placeholder = { Text(placeholder) },
        onValueChange = {
            if (it == "") {
                onChange(0)
            } else if (it.isDigitsOnly()) {
                onChange(max(minValue, min(maxValue, it.toIntSafe())))
            }
        },
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
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Composable
@Preview
private fun DFTextFieldPreview() = Preview {
    val text = remember { mutableIntStateOf(0) }
    DFNumberField(
        text = text,
        onChange = { text.intValue = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )
}
