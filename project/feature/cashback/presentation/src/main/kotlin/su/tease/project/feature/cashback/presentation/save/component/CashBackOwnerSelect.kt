package su.tease.project.feature.cashback.presentation.save.component

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
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFPlaceholder
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.presentation.R
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerText
import su.tease.project.feature.cashback.presentation.save.utls.FormFieldError

@Composable
fun CashBackOwnerSelect(
    cashBackOwnerState: State<CashBackOwner?>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    disabled: Boolean,
    cashBackOwnerPreviewView: CashBackOwnerPreviewView,
    cashBackOwnerText: CashBackOwnerText,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(cashBackOwnerText.cashBackOwnerTitle),
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.SaveCashBack_CashBackOwnerSelect_Error)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
                .thenIf(!disabled) { Modifier.clickable { onSelect() } }
                .background(Theme.colors.inputBackground)
                .fillMaxWidth()
                .height(Theme.sizes.size48)
                .padding(horizontal = Theme.sizes.padding10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            cashBackOwnerState.value?.let {
                CashBackOwnerPreview(it, cashBackOwnerPreviewView)
            } ?: run {
                DFPlaceholder(
                    text = stringResource(cashBackOwnerText.cashBackOwnerPlaceholder),
                    modifier = Modifier.padding(
                        horizontal = Theme.sizes.padding6,
                        vertical = Theme.sizes.padding6,
                    )
                )
            }
        }
    }
}
