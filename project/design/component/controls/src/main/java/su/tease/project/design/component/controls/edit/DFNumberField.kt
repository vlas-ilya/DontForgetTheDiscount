package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.theme.impl.utils.Preview
import kotlin.math.max
import kotlin.math.min

@Composable
@Suppress("LongParameterList")
fun DFNumberField(
    text: State<Int>,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    shouldRound: Boolean = false,
    showZero: Boolean = false,
    placeholder: String = "",
    action: DFTextFieldAction? = null,
) {
    DFTextField(
        text = text.map { if (showZero || it != 0) it.toString() else "" },
        onChange = {
            if (it == "") {
                onChange(0)
            } else if (it.isDigitsOnly()) {
                val value = it.toIntSafe()
                val roundedValue = max(minValue, min(maxValue, value))
                if (shouldRound || value == roundedValue) {
                    onChange(roundedValue)
                }
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = placeholder,
        action = action,
        maxLength = 11
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
