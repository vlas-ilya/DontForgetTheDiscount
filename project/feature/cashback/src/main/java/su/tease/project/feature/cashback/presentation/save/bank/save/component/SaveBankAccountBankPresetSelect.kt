package su.tease.project.feature.cashback.presentation.save.bank.save.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFPlaceholder
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.presentation.save.bank.save.utils.FormFieldError
import su.tease.project.feature.preset.api.domain.entity.BankPreset

@Composable
fun SaveBankAccountBankPresetSelect(
    bankState: State<BankPreset?>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier,
    customName: State<String?>? = null,
) {
    DFFormElement(
        label = stringResource(R.string.item_bank_preset_select_title),
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.item_bank_preset_select_error)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
                .clickable { onSelect() }
                .background(Theme.colors.inputBackground)
                .fillMaxWidth()
                .height(Theme.sizes.size48)
                .padding(horizontal = Theme.sizes.padding10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bankState.value?.let {
                SaveBankAccountBankPresetPreview(
                    bankPreset = it,
                    customName = customName?.value,
                    roundedIcon = true
                )
            } ?: run {
                DFPlaceholder(
                    text = stringResource(R.string.item_bank_preset_select_placeholder),
                    modifier = Modifier.padding(
                        horizontal = Theme.sizes.padding6,
                        vertical = Theme.sizes.padding6,
                    )
                )
            }
        }
    }
}
