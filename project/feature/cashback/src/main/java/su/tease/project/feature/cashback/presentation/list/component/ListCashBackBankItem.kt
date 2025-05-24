package su.tease.project.feature.cashback.presentation.list.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.preset.api.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.api.presentation.component.BankPresetIconSize

data class ListCashBackBankItem(
    private val bankAccount: BankAccount,
    private val store: Store<*>,
) : LazyListItem {

    override val key = BANK + bankAccount.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            modifier = Modifier.fillParentMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BankPresetIcon(
                bankPreset = bankAccount.bankPreset,
                size = BankPresetIconSize.DEFAULT,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding8),
            )

            DFText(
                text = bankAccount.customName,
                style = Theme.fonts.placeholder,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
            )
        }
    }
}

private const val BANK = "BANK"
