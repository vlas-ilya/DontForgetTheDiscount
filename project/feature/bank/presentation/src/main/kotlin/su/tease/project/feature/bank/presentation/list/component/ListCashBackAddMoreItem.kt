package su.tease.project.feature.bank.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.core.mvi.component.component.impl.NavigationComponent
import su.tease.core.mvi.component.component.impl.NavigationComponentImpl
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.button.DFLinkButton
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.dependencies.navigation.SaveCashBackPage

data class ListCashBackAddMoreItem(
    private val date: CashBackDate,
    private val bankAccount: BankAccount,
    private val store: Store<*>,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = BANK_ADD_MORE + bankAccount.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    SaveCashBackPage(
                        date = date,
                        bankAccount = bankAccount
                    ).forward()
                }
        ) {
            DFLinkButton(
                label = stringResource(R.string.Bank_ListBankPage_AddCashBackButton_Title),
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding8)
                    .padding(start = Theme.sizes.padding14),
            )
        }
    }
}

private const val BANK_ADD_MORE = "BANK_ADD_MORE"
