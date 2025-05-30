package su.tease.project.feature.cashback.presentation.list.component

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
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.presentation.save.SaveCashBackFeature
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest

data class ListCashBackAddMoreItem(
    private val date: CashBackDate,
    private val bankAccountItem: BankAccount,
    private val store: Store<*>,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = BANK_ADD_MORE + bankAccountItem.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    SaveCashBackFeature(
                        SaveCashBackRequest(
                            date = date,
                            bankAccount = bankAccountItem,
                        )
                    ).forward()
                }
        ) {
            DFLinkButton(
                label = stringResource(R.string.item_cash_back_in_list_button_add_more),
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding8)
                    .padding(start = Theme.sizes.padding14),
            )
        }
    }
}

private const val BANK_ADD_MORE = "BANK_ADD_MORE"
