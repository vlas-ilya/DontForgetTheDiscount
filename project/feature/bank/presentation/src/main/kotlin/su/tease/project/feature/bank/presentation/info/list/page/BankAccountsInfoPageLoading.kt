package su.tease.project.feature.bank.presentation.info.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.feature.bank.presentation.component.BankAccountsItem

@Composable
@NonSkippableComposable
fun BankAccountsInfoPageLoading(lazyListWrapper: LazyListWrapper) {
    lazyListWrapper.Shimmer(
        count = SHIMMER_ITEM_COUNT,
        modifier = Modifier.fillMaxWidth(),
        itemContent = { BankAccountsItem.Shimmer(it) },
        verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
        contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
    )
}

private const val SHIMMER_ITEM_COUNT = 30
