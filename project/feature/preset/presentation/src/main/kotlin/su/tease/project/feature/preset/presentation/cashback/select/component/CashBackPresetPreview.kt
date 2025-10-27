package su.tease.project.feature.preset.presentation.cashback.select.component

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
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.thenIfNotNull
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.presentation.cashback.select.reducer.CashBackPresetInfoDialogAction
import su.tease.project.feature.preset.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.presentation.component.CashBackPresetIconSize
import su.tease.project.design.icons.R as RIcons

data class CashBackPresetPreview(
    private val cashBackPreset: CashBackPreset,
    private val store: Store<*>,
    private val onClick: ((CashBackPreset) -> Unit)? = null,
) : LazyListItem {

    override val key = CASH_BACK_PRESET + cashBackPreset.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            modifier = Modifier
                .thenIfNotNull(onClick) { Modifier.clickable { it(cashBackPreset) } }
                .padding(horizontal = Theme.sizes.padding8)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CashBackPresetIcon(
                name = cashBackPreset.name,
                iconPreset = cashBackPreset.iconPreset,
                size = CashBackPresetIconSize.SMALL,
            )

            Spacer(modifier = Modifier.width(Theme.sizes.padding4))

            DFText(
                text = cashBackPreset.name,
                style = Theme.fonts.text,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize(),
            )
            runIf(cashBackPreset.info.isNotBlank() || cashBackPreset.mccCodes.isNotEmpty()) {
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFIconButton(
                    icon = RIcons.drawable.comment_info,
                    size = DFIconButtonSize.S,
                    onClick = {
                        store.dispatcher.dispatch(
                            CashBackPresetInfoDialogAction.OnShow(
                                cashBackPreset.cashBackOwnerPreset to cashBackPreset
                            )
                        )
                    }
                )
            }
        }
    }

    data class Shimmer(
        private val index: Int,
    ) : LazyListItem {

        override val key: String = "${CASH_BACK_PRESET}_SHIMMER_$index"

        @Composable
        override fun LazyItemScope.Compose() {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Theme.sizes.padding8)
                        .clip(CircleShape)
                        .background(Theme.colors.shimmer)
                        .padding(Theme.sizes.padding8)
                        .size(Theme.sizes.size24),
                )
                Box(
                    modifier = Modifier
                        .padding(end = Theme.sizes.padding6)
                        .clip(RoundedCornerShape(Theme.sizes.round12))
                        .fillMaxWidth()
                        .height(Theme.sizes.size32)
                        .background(Theme.colors.shimmer)
                )
            }
        }
    }
}

private const val CASH_BACK_PRESET = "CASH_BACK_PRESET"