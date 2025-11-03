package su.tease.project.feature.bank.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import su.tease.project.feature.bank.presentation.action.SaveCacheBackAction
import su.tease.project.feature.bank.presentation.action.invoke

data class BankAccountsPageAddMoreCashBackItem(
    private val date: CashBackDate,
    private val bankAccount: BankAccount,
    private val store: Store<*>,
    private val saveCacheBackAction: SaveCacheBackAction,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = BANK_ADD_MORE + bankAccount.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    store.dispatcher.dispatch(
                        saveCacheBackAction(
                            date = date,
                            bankAccount = bankAccount
                        )
                    )
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

    companion object {

        @Composable
        fun Shimmer() {
            Box(
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding8)
                    .clip(RoundedCornerShape(Theme.sizes.round12))
                    .fillMaxWidth()
                    .size(Theme.sizes.size22)
                    .background(Theme.colors.shimmer),
            )
        }
    }
}

private const val BANK_ADD_MORE = "BANK_ADD_MORE"
