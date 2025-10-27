package su.tease.project.feature.shop.presentation.list.page

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
import su.tease.project.feature.shop.presentation.component.ShopsItem
import su.tease.project.feature.shop.presentation.list.component.ShopsAddMoreCashBackItem
import su.tease.project.feature.shop.presentation.list.component.ShopsPageCashBackItem

@Composable
@NonSkippableComposable
fun ListCashBackLoading(lazyListWrapper: LazyListWrapper) {
    lazyListWrapper.Shimmer(
        count = SHIMMER_ITEM_COUNT,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
        contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
        itemContent = ::ListCashBackShimmer
    )
}

data class ListCashBackShimmer(
    val index: Int
) : LazyListItem {

    override val key = "${LIST_CASH_BACK_SHIMMER}_SHIMMER_$index"

    @Composable
    override fun LazyItemScope.Compose() {
        ShopsItem.Shimmer(0).run { Compose() }
        ShopsPageCashBackItem.Shimmer()
        ShopsPageCashBackItem.Shimmer()
        ShopsPageCashBackItem.Shimmer()
        ShopsAddMoreCashBackItem.Shimmer()
    }

    companion object {
        private const val LIST_CASH_BACK_SHIMMER = "LIST_CASH_BACK_SHIMMER"
    }
}

private const val SHIMMER_ITEM_COUNT = 20
