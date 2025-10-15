package su.tease.project.feature.preset.presentation.shop.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions as Save
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage as Select
import su.tease.project.feature.preset.presentation.shop.select.reducer.SelectShopPresetState as S

class SelectShopPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Select.OnInit -> S()
        is Select.OnSelectAction -> copy(savedCashBackOwnerPreset = null)
        is Save.OnSaveSuccess -> copy(savedCashBackOwnerPreset = action.cashBackOwnerPreset)
        else -> this
    }
}

@Parcelize
data class SelectShopPresetState(
    val savedCashBackOwnerPreset: ShopPreset? = null
) : State
