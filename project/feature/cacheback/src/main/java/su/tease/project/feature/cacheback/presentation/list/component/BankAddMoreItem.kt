package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.design.component.controls.button.DFLinkButton
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.preset.mapper.toPreset
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackState
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature

data class BankAddMoreItem(
    private val date: CacheBackDate,
    private val bankItem: Bank,
    private val store: Store<*>,
) : LazyListItem {

    override val key = bankItem.id.value + BANK_ADD_MORE

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    store.dispatcher.dispatch(
                        NavigationAction.ForwardToFeature(
                            SaveCacheBackFeature(
                                SaveCacheBackState(
                                    date = date,
                                    bank = bankItem.toPreset(),
                                )
                            )
                        )
                    )
                }
        ) {
            DFLinkButton(
                label = stringResource(R.string.item_cache_back_in_list_button_add_more),
                modifier = Modifier
                    .padding(horizontal = Theme.sizes.padding8)
                    .padding(start = Theme.sizes.padding14),
            )
        }
    }
}

private const val BANK_ADD_MORE = "BANK_ADD_MORE"
