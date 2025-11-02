package su.tease.project.design.component.controls.edit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.icons.R
import su.tease.project.design.theme.impl.utils.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
fun DFTextField(
    text: State<String>,
    onChange: (String) -> Unit,
    maxLength: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    placeholder: String = "",
    action: DFTextFieldAction? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = RoundedCornerShape(Theme.sizes.roundInfinity)
) {
    val isFocused = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier) {
        BasicTextField(
            value = text.value,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = Theme.fonts.placeholder.fontSize,
                fontStyle = Theme.fonts.placeholder.fontStyle,
                fontWeight = Theme.fonts.placeholder.fontWeight,
                fontFamily = Theme.fonts.placeholder.fontFamily,
                color = Theme.colors.text,
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    onChange(it)
                }
            },
            visualTransformation = visualTransformation,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(Theme.sizes.size48)
                .border(
                    width = isFocused.value.choose(
                        Theme.sizes.size2,
                        Theme.sizes.size0,
                    ),
                    color = isFocused.value.choose(
                        Theme.colors.accent,
                        Theme.colors.transparent,
                    ),
                    shape = shape
                )
                .clip(shape)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                }
                .thenIfNotNull(onClick) {
                    Modifier.clickable { it() }
                },
            interactionSource = interactionSource,
        ) {
            TextFieldDefaults.DecorationBox(
                value = text.value,
                shape = shape,
                contentPadding = TextFieldDefaults.contentPaddingWithLabel(
                    top = Theme.sizes.padding12,
                    bottom = Theme.sizes.padding12,
                    end = (action != null).choose(
                        Theme.sizes.size50,
                        Theme.sizes.padding8,
                    )
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

        action?.let {
            DFIconButton(
                icon = it.icon,
                onClick = { it.onClick() },
                modifier = Modifier
                    .padding(end = Theme.sizes.padding4)
                    .align(Alignment.CenterEnd)
                    .size(Theme.sizes.size40),
                background = Theme.colors.inputFocusedBorder,
                tint = Theme.colors.buttonText,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
fun DFTextField(
    text: TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
    maxLength: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    placeholder: String = "",
    action: DFTextFieldAction? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val isFocused = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = Theme.fonts.placeholder.fontSize,
                fontStyle = Theme.fonts.placeholder.fontStyle,
                fontWeight = Theme.fonts.placeholder.fontWeight,
                fontFamily = Theme.fonts.placeholder.fontFamily,
                color = Theme.colors.text,
            ),
            onValueChange = {
                if (it.text.length <= maxLength) {
                    onChange(it)
                }
            },
            visualTransformation = visualTransformation,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(Theme.sizes.size48)
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
                }
                .thenIfNotNull(onClick) {
                    Modifier.clickable { it() }
                },
            interactionSource = interactionSource,
        ) {
            TextFieldDefaults.DecorationBox(
                value = text.text,
                shape = RoundedCornerShape(Theme.sizes.roundInfinity),
                contentPadding = TextFieldDefaults.contentPaddingWithLabel(
                    top = Theme.sizes.padding12,
                    bottom = Theme.sizes.padding12,
                    end = (action != null).choose(
                        Theme.sizes.size50,
                        Theme.sizes.padding8,
                    )
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

        action?.let {
            DFIconButton(
                icon = it.icon,
                onClick = { it.onClick() },
                modifier = Modifier
                    .padding(end = Theme.sizes.padding4)
                    .align(Alignment.CenterEnd)
                    .size(Theme.sizes.size40),
                background = Theme.colors.inputFocusedBorder,
                tint = Theme.colors.buttonText,
            )
        }
    }
}

data class DFTextFieldAction(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
)

@Composable
@Preview
private fun DFTextFieldPreview() = Preview {
    val text = remember { mutableStateOf("") }
    DFTextField(
        text = text,
        onChange = { text.value = it },
        placeholder = "Test",
        maxLength = 100,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )
    DFTextField(
        text = text,
        onChange = { text.value = it },
        placeholder = "Test",
        maxLength = 100,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4)
    )

    DFTextField(
        text = text,
        onChange = { text.value = it },
        placeholder = "Test ",
        maxLength = 100,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.sizes.padding4),
        action = DFTextFieldAction(
            R.drawable.plus,
            onClick = {},
        )
    )
}
