package su.tease.project.feature.bank.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.Compose

data class BankAccountsPageBankItem(
    private val bankAccount: BankAccount,
    private val bankPresetIconView: BankPresetIconView,
    private val store: Store<*>,
    private val onClick: ((BankAccount) -> Unit)? = null
) : LazyListItem {

    override val key = BANK + bankAccount.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            modifier = Modifier
                .fillParentMaxWidth()
                .thenIfNotNull(onClick) { Modifier.clickable { it(bankAccount) } },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bankPresetIconView.Compose(
                iconPreset = bankAccount.preset.iconPreset,
                name = bankAccount.customName,
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
