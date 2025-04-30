package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.theme.impl.utils.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("LongMethod")
fun DFTextField(
    text: State<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    val isFocused = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text.value,
        textStyle = TextStyle(
            fontSize = Theme.fonts.placeholder.fontSize,
            fontStyle = Theme.fonts.placeholder.fontStyle,
            fontWeight = Theme.fonts.placeholder.fontWeight,
            fontFamily = Theme.fonts.placeholder.fontFamily,
            color = Theme.colors.text,
        ),
        onValueChange = { onChange(it) },
        modifier = modifier
            .border(
                width = isFocused.value.choose(
                    Theme.sizes.size2,
                    Theme.sizes.size0,
                ),
                color = isFocused.value.choose(
                    Theme.colors.accent,
                    Theme.colors.transparent,
                ),
                shape = RoundedCornerShape(Theme.sizes.roundInfinity)
            )
            .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
            .onFocusChanged {
                isFocused.value = it.isFocused
            },
        interactionSource = interactionSource,
        visualTransformation = VisualTransformation.None,
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                value = text.value,
                shape = RoundedCornerShape(Theme.sizes.roundInfinity),
                contentPadding = TextFieldDefaults.contentPaddingWithLabel(
                    top = Theme.sizes.padding12,
                    bottom = Theme.sizes.padding12,
                ),
                colors = TextFieldDefaults.colors().copy(
                    cursorColor = Theme.colors.text,

                    focusedContainerColor = Theme.colors.inputBackground,
                    unfocusedContainerColor = Theme.colors.inputBackground,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    focusedTextColor = Theme.colors.text,
                    unfocusedTextColor = Theme.colors.text,

                    focusedPlaceholderColor = Theme.colors.inputPlaceholder,
                    unfocusedPlaceholderColor = Theme.colors.inputPlaceholder,
                ),
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    DFText(
                        text = placeholder,
                        style = Theme.fonts.placeholder,
                        color = Theme.colors.inputPlaceholder,
                    )
                },
            )
        }
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
