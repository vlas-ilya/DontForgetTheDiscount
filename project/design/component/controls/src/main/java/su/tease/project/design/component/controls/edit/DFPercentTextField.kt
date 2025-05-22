package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.core.utils.ext.toPercent
import su.tease.project.design.theme.impl.utils.Preview

@Composable
@Suppress("LongParameterList")
fun DFPercentTextField(
    value: State<Int>,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxValue: Int = Int.MAX_VALUE,
    showZero: Boolean = false,
    placeholder: String = "",
    action: DFTextFieldAction? = null,
) {
    val initValue = if (value.value != 0 || showZero) value.value.toPercent(2) else ""
    val text = remember { mutableStateOf(TextFieldValue(text = initValue)) }

    DFTextField(
        text = text.value,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = placeholder,
        action = action,
        maxLength = 11,
        onChange = {
            val newValue = it.copy(text = it.text.replace(",", "."))
            if (newValue.text.isEmpty()) {
                text.value = TextFieldValue(text = "", selection = newValue.selection)
                onChange(0)
                return@DFTextField
            }

            if (newValue.text == ".") {
                text.value = TextFieldValue(
                    text = "0.",
                    selection = TextRange(newValue.selection.start + 1, newValue.selection.end + 1),
                )
                onChange(0)
                return@DFTextField
            }

            if (newValue.text == "0") {
                text.value = TextFieldValue(text = newValue.text, selection = newValue.selection)
                onChange(0)
                return@DFTextField
            }

            if (newValue.text.all { it.isDigit() } && newValue.text.startsWith("0")) {
                val integer = newValue.text.toInt()
                text.value = TextFieldValue(
                    text = "0.${integer.toString().take(2)}",
                    selection = TextRange(newValue.selection.start + 1, newValue.selection.end + 1)
                )
                onChange(integer.toString().rightPad(2, '0').toInt())
                return@DFTextField
            }

            if (newValue.text.all { it.isDigit() || it == '.' } && newValue.text.startsWith(".")) {
                val integer = newValue.text.drop(1).toInt()
                text.value = TextFieldValue(
                    text = "0.${integer.toString().take(2)}",
                    selection = TextRange(2, 2)
                )
                onChange(integer.toString().rightPad(2, '0').toInt())
                return@DFTextField
            }

            if (newValue.text.all { it.isDigit() }) {
                if (newValue.text.toInt() > maxValue) return@DFTextField
                text.value = TextFieldValue(text = newValue.text, selection = newValue.selection)
                onChange(newValue.text.toInt() * 100)
                return@DFTextField
            }

            if (newValue.text.all { it.isDigit() || it == '.' }) {
                val parts = newValue.text.replace("..", ".").split('.')
                val integer = parts[0].toIntSafe()
                val fractional = parts[1].take(2)
                if (integer + 1 > maxValue) return@DFTextField
                val selection = if (newValue.text.startsWith("0") && integer > 0) {
                    TextRange(newValue.selection.start - 1, newValue.selection.end - 1)
                } else {
                    newValue.selection
                }
                text.value = TextFieldValue(text = "${integer}.${fractional}", selection = selection)
                onChange(integer * 100 + fractional.rightPad(2, '0').toIntSafe())
                return@DFTextField
            }
        },
    )
}

private fun String.rightPad(n: Int, symbol: Char): String =
    if (length > n) {
        take(n)
    } else {
        this + List(n - length) { symbol.toString() }.joinToString(separator = "")
    }

@Composable
@Preview
private fun DFPercentTextFieldPreview() = Preview {
    val value1 = remember { mutableIntStateOf(155) }
    DFPercentTextField(
        value = value1,
        onChange = { value1.intValue = it }
    )
    Text(text = value1.intValue.toString())

    val value2 = remember { mutableIntStateOf(0) }
    DFPercentTextField(
        value = value2,
        onChange = { value2.intValue = it }
    )
    Text(text = value2.intValue.toString())
}
