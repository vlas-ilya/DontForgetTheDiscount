package su.tease.project.feature.cashback.presentation.save.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.dependencies.view.ComposeIcon

@Composable
fun CashBackOwnerPreview(
    cashBackOwner: CashBackOwner,
    cashBackOwnerPreviewView: CashBackOwnerPreviewView,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) = cashBackOwner.run {
    Row(
        modifier = modifier.thenIfNotNull(onClick) { Modifier.clickable { it() } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        cashBackOwnerPreviewView.ComposeIcon(
            name = cashBackOwner.preset.name,
            icon = cashBackOwner.preset.iconPreset,
        )
        DFText(
            text = customName,
            style = Theme.fonts.placeholder,
            modifier = Modifier.padding(horizontal = Theme.sizes.padding6),
            maxLines = 1,
        )
    }
}
