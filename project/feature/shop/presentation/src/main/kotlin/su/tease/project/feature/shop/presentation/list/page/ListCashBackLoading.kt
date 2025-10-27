package su.tease.project.feature.shop.presentation.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.shop.presentation.component.ShopsItem
import su.tease.project.feature.shop.presentation.list.component.ShopsAddMoreCashBackItem
import su.tease.project.feature.shop.presentation.list.component.ShopsPageCashBackItem

@Composable
fun ListCashBackLoading() {
    Shimmer {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(Theme.sizes.padding4)
        ) {
            Spacer(Modifier.height(Theme.sizes.padding8))
            repeat(SHIMMER_ITEM_COUNT) {
                ShopsItem.Shimmer()
                ShopsPageCashBackItem.Shimmer()
                ShopsPageCashBackItem.Shimmer()
                ShopsPageCashBackItem.Shimmer()
                ShopsAddMoreCashBackItem.Shimmer()
            }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
