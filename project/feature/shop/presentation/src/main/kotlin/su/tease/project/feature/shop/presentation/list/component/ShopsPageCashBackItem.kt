package su.tease.project.feature.shop.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.core.mvi.component.component.impl.NavigationComponent
import su.tease.core.mvi.component.component.impl.NavigationComponentImpl
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.toPercent
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.FRACTIONAL_SIZE
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.R

import su.tease.project.feature.shop.presentation.action.SaveCacheBackAction
import su.tease.project.feature.shop.presentation.action.invoke
import su.tease.project.feature.shop.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.shop.presentation.dependencies.view.Compose
import su.tease.project.feature.shop.presentation.list.reducer.CashBackInfoDialogAction
import su.tease.project.design.icons.R as RIcons

data class ShopsPageCashBackItem(
    private val shop: Shop,
    private val cashBack: CashBack,
    private val cashBackPresetIconView: CashBackPresetIconView,
    private val store: Store<*>,
    private val saveCacheBackAction: SaveCacheBackAction,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = CASH_BACK + cashBack.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    store.dispatcher.dispatch(
                        saveCacheBackAction(
                            id = cashBack.id,
                            shop = shop,
                            cashBackPreset = cashBack.preset,
                            size = cashBack.size,
                            date = cashBack.date,
                        )
                    )
                }
                .padding(horizontal = Theme.sizes.padding8)
                .padding(start = Theme.sizes.padding20),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            cashBackPresetIconView.Compose(cashBack.preset)
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DFText(
                    text = stringResource(
                        R.string.Shop_ListShopPage_CashBackItem_Percent_Text,
                        cashBack.size.toPercent(FRACTIONAL_SIZE),
                    ),
                    style = Theme.fonts.monospace,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFText(
                    text = cashBack.preset.name,
                    style = Theme.fonts.text,
                    maxLines = 1,
                )
            }
            runIf(cashBack.preset.info.isNotBlank() || cashBack.preset.mccCodes.isNotEmpty()) {
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFIconButton(
                    icon = RIcons.drawable.comment_info,
                    size = DFIconButtonSize.S,
                    onClick = {
                        store.dispatcher.dispatch(
                            CashBackInfoDialogAction.OnShow(shop to cashBack)
                        )
                    },
                    tint = Theme.colors.iTint,
                )
            }
        }
    }

    companion object {

        @Composable
        fun Shimmer() {
            Row(
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding8)
                    .padding(start = Theme.sizes.padding20),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Theme.sizes.size40)
                        .background(Theme.colors.shimmer),
                )
                Box(
                    modifier = Modifier
                        .padding(start = Theme.sizes.padding4)
                        .clip(RoundedCornerShape(Theme.sizes.round12))
                        .fillMaxWidth()
                        .height(Theme.sizes.size32)
                        .background(Theme.colors.shimmer)
                )
            }
        }
    }
}

private const val CASH_BACK = "CASH_BACK"
