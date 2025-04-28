package su.tease.project.design.component.controls.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.theme.impl.utils.Preview
import kotlin.math.max
import kotlin.math.min

@Composable
fun DFNumberField(
    text: State<Int>,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    showZero: Boolean = false,
    onChange: (Int) -> Unit,
) {
    TextField(
        value = text.value.toString()
            .let { if (showZero || it != "0") it else "" },
        onValueChange = {
            if (it == "") {
                onChange(0)
            } else if (it.isNotEmpty() || it.matches(pattern)) {
                onChange(max(minValue, min(maxValue, it.toIntSafe())))
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

private val pattern = Regex("^\\d+\$")

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
