package su.tease.project.feature.shop.presentation.info.list.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.shop.presentation.component.ShopsItem

@Composable
fun ShopsInfoPageLoading() {
    Shimmer {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(Theme.sizes.padding4)
        ) {
            Spacer(Modifier.height(Theme.sizes.padding4))
            repeat(SHIMMER_ITEM_COUNT) { ShopsItem.Shimmer() }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
