package su.tease.project.design.component.controls.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.icon.DFIcon
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.theme.impl.utils.Preview
import su.tease.project.design.component.controls.R

@Composable
@NonSkippableComposable
fun DFFormElement(
    label: String,
    modifier: Modifier = Modifier,
    info: State<String?>,
    error: State<String?>,
    content: @Composable Callback,
) {
    DFFormElement(
        label = label,
        modifier = modifier,
        info = info.value,
        error = error.value,
        content = content,
    )
}

@Composable
fun DFFormElement(
    label: String,
    modifier: Modifier = Modifier,
    info: String? = null,
    error: String? = null,
    content: @Composable Callback,
) {
    Column(
        modifier = modifier
    ) {
        DFText(
            text = label,
            style = Theme.fonts.smallTitle,
            color = Theme.colors.header,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(Theme.sizes.padding4))

        content()

        runIf(info != null && error != null) {
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
        }

        info?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                DFIcon(
                    icon = R.drawable.exclamation,
                    modifier = Modifier.size(Theme.sizes.size12),
                    tint = Theme.colors.info,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding2))
                DFText(
                    text = it,
                    style = Theme.fonts.smallInfo,
                    color = Theme.colors.info,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        error?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                DFIcon(
                    icon = R.drawable.cross_circle,
                    modifier = Modifier.size(Theme.sizes.size12),
                    tint = Theme.colors.error,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding2))
                DFText(
                    text = it,
                    style = Theme.fonts.smallInfo,
                    color = Theme.colors.error,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
@Preview
private fun DFFormElementPreview() = Preview {
    val text = remember { mutableStateOf("") }
    DFFormElement(
        label = "Поле ввода:",
        error = "Заполнено некорректно!",
        info = "Нужно заполнять корректно",
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        DFTextField(
            text = text,
            onChange = { text.value = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}