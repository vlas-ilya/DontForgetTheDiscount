package su.tease.project.design.component.controls.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.text.DFText

@Composable
fun DFButton(
    label: String,
    onClick: Callback,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        DFText(text = label)
    }
}