package su.tease.project.feature.bank.presentation.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.bank.presentation.component.BankAccountsItem
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageAddMoreCashBackItem
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageCashBackItem

@Composable
fun BankAccountsPageLoading(
    modifier: Modifier = Modifier,
) {
    Shimmer(
        modifier = modifier,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding6)) {
            Spacer(Modifier.height(Theme.sizes.padding2))
            repeat(SHIMMER_ITEM_COUNT) {
                BankAccountsItem.Shimmer()
                BankAccountsPageCashBackItem.Shimmer()
                BankAccountsPageCashBackItem.Shimmer()
                BankAccountsPageCashBackItem.Shimmer()
                BankAccountsPageAddMoreCashBackItem.Shimmer()
            }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
