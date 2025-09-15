package su.tease.project.feature.bank.presentation.list.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.bank.presentation.R

@Composable
fun ListCashBackFailed(
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        DFTextH1(
            text = stringResource(R.string.Bank_ListBankPage_ErrorResult_Placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Theme.sizes.padding20)
                .padding(horizontal = Theme.sizes.padding8),
            color = Theme.colors.accent,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.weight(1F))
        DFButton(
            label = stringResource(R.string.Bank_ListBankPage_Error_TryAgainButton),
            onClick = { onTryAgain() },
            shape = RoundedCornerShape(Theme.sizes.round12),
            modifier = Modifier
                .wrapContentHeight()
                .padding(Theme.sizes.padding8),
        )
    }
}
