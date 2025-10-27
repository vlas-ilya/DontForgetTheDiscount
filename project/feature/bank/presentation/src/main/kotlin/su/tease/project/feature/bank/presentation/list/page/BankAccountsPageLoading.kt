package su.tease.project.feature.bank.presentation.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.feature.bank.presentation.component.BankAccountsItem
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageAddMoreCashBackItem
import su.tease.project.feature.bank.presentation.list.component.BankAccountsPageCashBackItem

@Composable
@NonSkippableComposable
fun BankAccountsPageLoading(
    lazyListWrapper: LazyListWrapper,
) {
    lazyListWrapper.Shimmer(
        count = SHIMMER_ITEM_COUNT,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
        contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
        itemContent = ::ListCashBackShimmer,
    )
}

data class ListCashBackShimmer(
    val index: Int
) : LazyListItem {

    override val key = "${LIST_CASH_BACK_SHIMMER}_SHIMMER_$index"

    @Composable
    override fun LazyItemScope.Compose() {
        BankAccountsItem.Shimmer(0).run { Compose() }
        BankAccountsPageCashBackItem.Shimmer()
        BankAccountsPageCashBackItem.Shimmer()
        BankAccountsPageCashBackItem.Shimmer()
        BankAccountsPageAddMoreCashBackItem.Shimmer()
    }

    companion object {
        private const val LIST_CASH_BACK_SHIMMER = "LIST_CASH_BACK_SHIMMER"
    }
}

private const val SHIMMER_ITEM_COUNT = 20
