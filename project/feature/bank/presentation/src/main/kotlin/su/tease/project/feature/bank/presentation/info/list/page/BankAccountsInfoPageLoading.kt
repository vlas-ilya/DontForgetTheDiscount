package su.tease.project.feature.bank.presentation.info.list.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.shimmer.Shimmer

@Composable
fun BankAccountsInfoPageLoading(
    modifier: Modifier = Modifier,
) {
    Shimmer(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(Theme.sizes.padding4)
        ) {
            Spacer(Modifier.height(Theme.sizes.padding8))
            repeat(SHIMMER_ITEM_COUNT) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Theme.sizes.padding8)
                        .clip(RoundedCornerShape(Theme.sizes.round12))
                        .fillMaxWidth()
                        .height(Theme.sizes.size48)
                        .background(Theme.colors.shimmer)
                )
            }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
