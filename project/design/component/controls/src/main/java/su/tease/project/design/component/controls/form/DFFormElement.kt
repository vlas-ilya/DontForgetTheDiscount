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
import su.tease.project.design.component.controls.R
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.icon.DFIcon
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.theme.impl.utils.Preview

@Composable
@NonSkippableComposable
@Suppress("LongParameterList")
fun DFFormElement(
    label: String,
    info: State<String?>,
    error: State<String?>,
    modifier: Modifier = Modifier,
    noError: Boolean = false,
    content: @Composable () -> Unit,
) {
    DFFormElement(
        label = label,
        modifier = modifier,
        info = info.value,
        error = error.value,
        noError = noError,
        content = content,
    )
}

@Composable
@Suppress("LongParameterList")
fun DFFormElement(
    label: String,
    modifier: Modifier = Modifier,
    info: String? = null,
    error: String? = null,
    noError: Boolean = false,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        DFText(
            text = label.uppercase(),
            style = Theme.fonts.smallTitle,
            color = Theme.colors.text,
            modifier = Modifier.padding(start = Theme.sizes.padding4),
        )

        Spacer(modifier = Modifier.height(Theme.sizes.padding2))

        content()

        info?.let {
            Spacer(modifier = Modifier.height(Theme.sizes.padding2))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = Theme.sizes.padding4),
            ) {
                DFIcon(
                    icon = R.drawable.exclamation,
                    modifier = Modifier.size(Theme.sizes.size10),
                    tint = Theme.colors.info,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding2))
                DFText(
                    text = it,
                    style = Theme.fonts.smallInfo,
                    color = Theme.colors.info,
                    modifier = Modifier,
                )
            }
        }

        error?.let {
            Spacer(modifier = Modifier.height(Theme.sizes.padding2))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = Theme.sizes.padding4),
            ) {
                DFIcon(
                    icon = R.drawable.cross_circle,
                    modifier = Modifier.size(Theme.sizes.size10),
                    tint = Theme.colors.error,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding2))
                DFText(
                    text = it,
                    style = Theme.fonts.smallInfo,
                    color = Theme.colors.error,
                    modifier = Modifier,
                )
            }
        } ?: runIf(!noError) {
            Spacer(modifier = Modifier.size(Theme.sizes.size16))
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
